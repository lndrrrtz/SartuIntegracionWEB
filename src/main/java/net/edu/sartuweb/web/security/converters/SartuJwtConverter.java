package net.edu.sartuweb.web.security.converters;

import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import net.edu.sartuweb.core.beans.UsuarioAutenticado;

public class SartuJwtConverter extends JwtAccessTokenConverter {

	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
		
		OAuth2Authentication auth = super.extractAuthentication(map);
		
		UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado();
		usuarioAutenticado.setUsername((String)map.get("user_name"));
		usuarioAutenticado.setId((String)map.get("id"));
		usuarioAutenticado.setNombre((String)map.get("nombre"));
		usuarioAutenticado.setDni((String)map.get("dni"));
		usuarioAutenticado.setEmail((String)map.get("email"));
		usuarioAutenticado.setAuthorities(auth.getAuthorities());
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuarioAutenticado, null, auth.getAuthorities());
		return new OAuth2Authentication(auth.getOAuth2Request(), authenticationToken);
	}
}
