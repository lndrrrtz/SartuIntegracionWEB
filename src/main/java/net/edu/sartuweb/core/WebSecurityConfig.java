package net.edu.sartuweb.core;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

import net.edu.sartuweb.web.security.SartuJwtConverter;
import net.edu.sartuweb.web.security.SartuLogoutHandler;
import net.edu.sartuweb.web.security.SartuLogoutSuccessHandler;

/**
 * Configura la autenticación y los filtros de seguridad para la aplicación.
 * 
 * Personaliza la configuración de seguridad de Spring Security.
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	// JWKS
	private static final String N = "n"; 
	private static final String E = "e";
	private static final String RSA = "RSA"; 
	private static final String KEYS = "keys"; 
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;
	
	@Value("${openid.accessTokenUri}")
	private String accessTokenUri;

	@Value("${openid.userAuthorizationUri}")
	private String userAuthorizationUri;

	@Value("${openid.clientId}")
	private String clientId;

	@Value("${openid.clientSecret}")
	private String clientSecret;
	
	@Value("${openid.scope}")
	private String scope;
	
	@Value("${openid.redirecturi}")
	private String redirectUri;
	
	@Value("${jwt.publicKeyUri}")
	private String publicKeyUri;

//	@Value("${openid.converter.signingKey}")
//	private String signingKey;
	
	@Value("${jwt.publicKey}")
	private Resource privateKeyResource;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/recursos/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.addFilterAfter(oAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
			.addFilterAfter(ssoFilter(), OAuth2ClientContextFilter.class).httpBasic()
			.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().authorizeRequests()
			
			.and()
			.authorizeRequests()	
				.antMatchers("/").permitAll()
				.anyRequest().authenticated()
			
				.and()
				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
			
			.httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
			
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.addLogoutHandler(sartuLogoutHandler())
//				.logoutSuccessHandler(sartuLogoutSuccessHandler())
				.invalidateHttpSession(true).clearAuthentication(true)
				.deleteCookies("JSESSIONID")
			
			.and()
				.csrf().disable();
	}
	
	/**
	 * Define un bean que se utiliza para realizar solicitudes REST con OAuth 2
	 * 
	 * @return {@link OAuth2RestTemplate} confirado con los datos de Sartu (SSO) y el contexto de OAuth 2
 	 */
	@Bean
	public OAuth2RestTemplate sartuRestTemplate() {
		return new OAuth2RestTemplate(oauthResource(), oauth2ClientContext);
	}
	
	/**
	 * Define el filtro que se encarga del contexto de OAuth 2
	 * 
	 * @return {@link OAuth2ClientContextFilter) 
	 */
	@Bean
	public OAuth2ClientContextFilter oAuth2ClientContextFilter() {
		return new OAuth2ClientContextFilter();
	}
	
	/**
	 * Configura el filtro que se encarga de la autenticación del cliente OAuth 2
	 * 
	 * @return {@link OAuth2ClientAuthenticationProcessingFilter} con la configuración para manejar la identificación del usuario
	 */
	@Bean
	public OAuth2ClientAuthenticationProcessingFilter ssoFilter() {
//		Oauth2ClientFilter sartuFilter = new Oauth2ClientFilter("/");
		// Crear sartuFilter para que procese/detecte la URL "/" y, al acceder, redirija a Sartu (SSO)
		OAuth2ClientAuthenticationProcessingFilter sartuFilter = new OAuth2ClientAuthenticationProcessingFilter("/");
//		OauthFilter oauthFilter = new OauthFilter("/");
		// Asigna RestTemplate al filtro
		sartuFilter.setRestTemplate(sartuRestTemplate());
		// Configura el servicio para gestionar los tokens
		sartuFilter.setTokenServices(tokenServices());
		// Configura la URL a la que se redireccionará en caso de éxito en la autenticación
		sartuFilter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/inicio"));
//		filter.setTokenServices(new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId()));
		
		// Devuelve el filtro
		return sartuFilter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(Boolean.TRUE);
		return defaultTokenServices;
	}

	/**
	 * Define un TokenStore para almacenar y convertir los tokens JWT
	 * 
	 * @return {@link TokenStore} con el convertidor de tokens JWT
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	/**
	 * Define JwtAccessTokenConverter que se encarga de convertir tokens JWT. Codifica y decodifica el token JWT y 
	 * verica su firma RSA
	 * 
	 * @return {@link JwtAccessTokenConverter} configurada con el verificador de firma RSA
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		SartuJwtConverter converter = new SartuJwtConverter();
		try {
			converter.setVerifier(rsaVerifier());
		} catch (Exception e) {
			LOGGER.debug("Error al asignar verificador a JWTAccessTokenConverter", e);
		}
		return converter;
	}

	/**
	 * Configura los datos del recurso protegido Sartu (SSO)
	 * @return {@link OAuth2ProtectedResourceDetails} con los datos necesarios para utilizar Sartu (SSO) 
	 */
	private OAuth2ProtectedResourceDetails oauthResource() {
		AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setAccessTokenUri(accessTokenUri);
		resource.setUserAuthorizationUri(userAuthorizationUri);
		resource.setScope(Arrays.asList(scope));
		resource.setPreEstablishedRedirectUri(redirectUri);
		return resource;
	}
	
	/*
	@Bean
	public RsaVerifier rsaVerifier() throws Exception {
		String publicKeyPEM = new String(Files.readAllBytes(privateKeyResource.getFile().toPath()));
		publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "")
					.replace("-----END PUBLIC KEY-----", "")
					.replaceAll("\\s", "");
		byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return new RsaVerifier((RSAPublicKey) publicKey);
	}
	*/
	
	@Bean
	public RsaVerifier rsaVerifier() throws Exception {
		Map<String, Object> jwks = restTemplate.getForObject(publicKeyUri, Map.class);
		Map<String, Object> jwk = (Map<String, Object>) ((java.util.List) jwks.get(KEYS)).get(0);

		String n = (String) jwk.get(N);
		String e = (String) jwk.get(E);

		BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(n));
		BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(e));

		RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PublicKey publicKey = keyFactory.generatePublic(spec);

		return new RsaVerifier((RSAPublicKey)publicKey);
	}
	
	@Bean
	public LogoutHandler sartuLogoutHandler() {
		return new SartuLogoutHandler();
	}

	@Bean
	public LogoutSuccessHandler sartuLogoutSuccessHandler() {
		return new SartuLogoutSuccessHandler();
	}
}
