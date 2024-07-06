package net.edu.sartuweb.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public abstract class Roles {
	
	/**
	 * Usuario normal
	 */
	public static final GrantedAuthority AUTH_NORMAL = new SimpleGrantedAuthority("NORMAL");
	
	/**
	 * Administrador
	 */	
	public static final GrantedAuthority AUTH_ADMINISTRADOR = new SimpleGrantedAuthority("ADMINISTRADOR");

	private Roles() {
		super();
	}
		
	/**
	 * @param rol Código del rol
	 * @return El rol correspondiente
	 */
	public static final GrantedAuthority valueOf(short rol) {
		switch (rol) {
		case 1:
			return AUTH_NORMAL;
		case 2:
			return AUTH_ADMINISTRADOR;
		default:
			throw new IllegalArgumentException("No se soporta el rol " + rol);	
		}
	}
}
