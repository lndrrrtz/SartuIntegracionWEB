package net.edu.sartuweb.web.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SartuAuthenticationToken extends AbstractAuthenticationToken {
	
	private static final long serialVersionUID = -1749049370674017950L;
	
	private final UserDetails principal;
	private final String credentials;

	public SartuAuthenticationToken() {
		super(null);
		this.principal = null;
		this.credentials = null;
		setAuthenticated(false);
	}

	public SartuAuthenticationToken(UserDetails principal,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = null;
		super.setAuthenticated(true);
	}

	@Override
	public String getCredentials() {
		return credentials;
	}

	@Override
	public UserDetails getPrincipal() {
		return principal;
	}
}
