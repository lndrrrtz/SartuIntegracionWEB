package net.edu.sartuweb.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.filter.OAuth2AuthenticationFailureEvent;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;


public class Oauth2ClientFilter extends OAuth2ClientAuthenticationProcessingFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(Oauth2ClientFilter.class);
	
	@Autowired
	private DefaultTokenServices tokenServices;
	
	private ApplicationEventPublisher eventPublisher;
	
	@Override
	public void setTokenServices(ResourceServerTokenServices tokenServices) {
//		this.tokenServices = tokenServices;
	}
	
//	private ApplicationEventPublisher eventPublisher;
	
	
	public Oauth2ClientFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
//		setAuthenticationManager(new NoopAuthenticationManager());
        setAuthenticationSuccessHandler(new WebSuccessHandler());
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		OAuth2AccessToken accessToken;
		try {
			accessToken = restTemplate.getAccessToken();
		} catch (OAuth2Exception e) {
			BadCredentialsException bad = new BadCredentialsException("Could not obtain access token", e);
			publish(new OAuth2AuthenticationFailureEvent(bad));
			throw bad;
		}
		try {
			
			return tokenServices.loadAuthentication(accessToken.getValue());
			
//			Map<String,Object> details = (Map)oAuth2Authentication.getDetails();
//			
//			UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado();
//			usuarioAutenticado.setUsername((String)details.get("user_name"));
//			usuarioAutenticado.setId((String)details.get("id"));
//			usuarioAutenticado.setNombre((String)details.get("nombre"));
//			usuarioAutenticado.setDni((String)details.get("dni"));
//			usuarioAutenticado.setEmail((String)details.get("email"));
//			
//			SartuAuthenticationToken sartuAuthenticationToken = new SartuAuthenticationToken(usuarioAutenticado, oAuth2Authentication.getAuthorities());
//			sartuAuthenticationToken.setDetails(oAuth2Authentication.getDetails());
//			
//			publish(new AuthenticationSuccessEvent(sartuAuthenticationToken));
//			
//			return sartuAuthenticationToken;
		}
		catch (InvalidTokenException e) {
			String mensajeError = "No se puede obtener los detalles del usuario mediante el token";
			LOGGER.error(mensajeError, e);
			BadCredentialsException bad = new BadCredentialsException(mensajeError, e);
			publish(new OAuth2AuthenticationFailureEvent(bad));
			throw bad;
		}
		
	}

	private void publish(ApplicationEvent event) {
		if (eventPublisher!=null) {
			eventPublisher.publishEvent(event);
		}
	}
	
//	private static class NoopAuthenticationManager implements AuthenticationManager {
//
//		@Override
//		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//			throw new UnsupportedOperationException("No se debe realizar ninguna autenticación con este AuthenticationManager");
//		}
//
//	}
}