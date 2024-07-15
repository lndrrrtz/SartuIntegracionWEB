package net.edu.sartuweb.web.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import net.edu.sartuweb.core.beans.UsuarioAutenticado;

public class SartuOidcLogoutHandler2  implements LogoutHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SartuOidcLogoutHandler2.class);

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		if (authentication != null && authentication.getDetails() != null) {
		
			UsuarioAutenticado usuarioAutenticado = (UsuarioAutenticado)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
//			String redirectUri = StringUtils.join("https://desatezain.sare.gipuzkoa.net:443/oidc/logout?id_token_hint=", usuarioAutenticado.getTokenResponse().getIdToken(), 
//					"&post_logout_redirect_uri=", "http://localhost:9081/SartuIntegracionWEB", "&state=" , UUID.randomUUID().toString());
			
			String redirectUri = "hjlkj";
			
			try {
				response.sendRedirect(redirectUri);
			} catch (IOException e) {
//				LOGGER.error("Error al cerrar la sesión {} del usuario {} ({})", usuarioAutenticado.getTokenResponse().getIdToken(), usuarioAutenticado.getNombre(), usuarioAutenticado.getId());
			} 
			
		} else {
			try {
				response.sendRedirect("/SartuIntegracionWEB");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
