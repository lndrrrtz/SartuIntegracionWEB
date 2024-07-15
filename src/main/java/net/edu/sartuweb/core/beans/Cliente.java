
package net.edu.sartuweb.core.beans;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 3556796853976997552L;

	@NotEmpty
	private String clientId;

	@NotEmpty
	private String clientSecret;

	@NotEmpty
	private String scope;

	@NotEmpty
	private String resourceIds;

	@NotEmpty
	private String authorizedGrantTypes;

	@NotEmpty
	private String registeredRedirectUris;

	@NotEmpty
	private String autoApproveScopes;

	@NotEmpty
	private String authorities;

	@NotNull
	private Integer accessTokenValiditySeconds;

	@NotNull
	private Integer refreshTokenValiditySeconds;

	private String additionalInformation;
	
	@NotEmpty
	private String descripcion;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public String getRegisteredRedirectUris() {
		return registeredRedirectUris;
	}

	public void setRegisteredRedirectUris(String registeredRedirectUris) {
		this.registeredRedirectUris = registeredRedirectUris;
	}

	public String getAutoApproveScopes() {
		return autoApproveScopes;
	}

	public void setAutoApproveScopes(String autoApproveScopes) {
		this.autoApproveScopes = autoApproveScopes;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
