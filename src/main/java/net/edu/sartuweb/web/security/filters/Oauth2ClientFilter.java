package net.edu.sartuweb.web.security.filters;

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

import net.edu.sartuweb.web.security.handlers.WebSuccessHandler;


public class Oauth2ClientFilter extends OAuth2ClientAuthenticationProcessingFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(Oauth2ClientFilter.class);
	
	@Autowired
	private DefaultTokenServices tokenServices;
	
	private ApplicationEventPublisher eventPublisher;
	
	public Oauth2ClientFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
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
}