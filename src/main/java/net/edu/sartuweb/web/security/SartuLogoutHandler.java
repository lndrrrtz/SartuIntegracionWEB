package net.edu.sartuweb.web.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class SartuLogoutHandler  implements LogoutHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SartuLogoutHandler.class);

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		if (authentication != null && authentication.getDetails() != null) {
		
			try {
				String url = StringUtils.join("http://localhost:9081/SartuOauthWEB/oauth/logout?id_token_hint=", oAuth2RestTemplate.getAccessToken().getValue(), "&post_logout_redirect_uri=http://localhost:9081/SartuIntegracionWEB/");
				response.sendRedirect(url);
			} catch (IOException e) {
				LOGGER.error("Error al hacer logout", e);
			}
			
		} else {
			try { 
				response.sendRedirect("/SartuIntegracionWEB");
			} catch (IOException e) {
				LOGGER.error("Error al redirigir a página principal", e);
			}
		}
	}
}
