package net.edu.sartuweb.web.security;

import java.util.ArrayList;
import java.util.Collection;

/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// package org.springframework.boot.autoconfigure.security.oauth2.resource;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * {@link ResourceServerTokenServices} that uses a user info REST service.
 *
 * @author Dave Syer
 * @since 1.3.0
 */
public class UserInfoTokenServices implements ResourceServerTokenServices {

	protected final Log logger = LogFactory.getLog(getClass());

	private final String userInfoEndpointUrl;

	private final String clientId;

	private OAuth2RestOperations restTemplate;

	private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

	private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();

	private PrincipalExtractor principalExtractor = new FixedPrincipalExtractor();

	public UserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
		this.userInfoEndpointUrl = userInfoEndpointUrl;
		this.clientId = clientId;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public void setRestTemplate(OAuth2RestOperations restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor) {
		Assert.notNull(authoritiesExtractor, "AuthoritiesExtractor must not be null");
		this.authoritiesExtractor = authoritiesExtractor;
	}

	public void setPrincipalExtractor(PrincipalExtractor principalExtractor) {
		Assert.notNull(principalExtractor, "PrincipalExtractor must not be null");
		this.principalExtractor = principalExtractor;
	}

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {
		Map<String, Object> map = getMap(this.userInfoEndpointUrl, accessToken);
		if (map.containsKey("error")) {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("userinfo returned error: " + map.get("error"));
			}
			throw new InvalidTokenException(accessToken);
		}
		return extractAuthentication(map);
	}

	private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
		Object principal = getPrincipal(map);
		List<GrantedAuthority> authorities = this.authoritiesExtractor.extractAuthorities(map);
		OAuth2Request request = new OAuth2Request(null, this.clientId, null, true, null, null, null, null, null);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "N/A",
				authorities);
		token.setDetails(map);
		return new OAuth2Authentication(request, token);
	}

	/**
	 * Return the principal that should be used for the token. The default implementation
	 * delegates to the {@link PrincipalExtractor}.
	 * @param map the source map
	 * @return the principal or {@literal "unknown"}
	 */
	protected Object getPrincipal(Map<String, Object> map) {
		Object principal = this.principalExtractor.extractPrincipal(map);
		return (principal == null ? "unknown" : principal);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException("Not supported: read access token");
	}

	@SuppressWarnings({ "unchecked" })
	private Map<String, Object> getMap(String path, String accessToken) {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Getting user info from: " + path);
		}
		try {
			OAuth2RestOperations restTemplate = this.restTemplate;
			if (restTemplate == null) {
				BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
				resource.setClientId(this.clientId);
				restTemplate = new OAuth2RestTemplate(resource);
			}
			OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext().getAccessToken();
			if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
				DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
				token.setTokenType(this.tokenType);
				restTemplate.getOAuth2ClientContext().setAccessToken(token);
			}
			return restTemplate.getForEntity(path, Map.class).getBody();
		}
		catch (Exception ex) {
			this.logger.warn("Could not fetch user details: " + ex.getClass() + ", " + ex.getMessage());
			return Collections.<String, Object>singletonMap("error", "Could not fetch user details");
		}
	}
	
	/**
	 * Strategy used by {@link UserInfoTokenServices} to extract authorities from the resource
	 * server's response.
	 *
	 * @author Dave Syer
	 * @since 1.3.0
	 */
	public interface AuthoritiesExtractor {

		/**
		 * Extract the authorities from the resource server's response.
		 * @param map the response
		 * @return the extracted authorities
		 */
		List<GrantedAuthority> extractAuthorities(Map<String, Object> map);

	}
	
	/**
	 * Default implementation of {@link PrincipalExtractor}. Extracts the principal from the
	 * map with well known keys.
	 *
	 * @author Phillip Webb
	 * @since 1.4.0
	 */
	public class FixedPrincipalExtractor implements PrincipalExtractor {

		private final String[] PRINCIPAL_KEYS = new String[] { "user", "username", "userid", "user_id", "login",
				"id", "name" };

		@Override
		public Object extractPrincipal(Map<String, Object> map) {
			for (String key : PRINCIPAL_KEYS) {
				if (map.containsKey(key)) {
					return map.get(key);
				}
			}
			return null;
		}

	}
	
	/**
	 * Strategy used by {@link UserInfoTokenServices} to extract the principal from the
	 * resource server's response.
	 *
	 * @author Phillip Webb
	 * @since 1.4.0
	 */
	public interface PrincipalExtractor {

		/**
		 * Extract the principal that should be used for the token.
		 * @param map the source map
		 * @return the extracted principal or {@code null}
		 */
		Object extractPrincipal(Map<String, Object> map);

	}
	
	/**
	 * Default implementation of {@link AuthoritiesExtractor}. Extracts the authorities from
	 * the map with the key {@code authorities}. If no such value exists, a single
	 * {@code ROLE_USER} authority is returned.
	 *
	 * @author Dave Syer
	 * @since 1.3.0
	 */
	public class FixedAuthoritiesExtractor implements AuthoritiesExtractor {

		private static final String AUTHORITIES = "authorities";

		private final String[] AUTHORITY_KEYS = { "authority", "role", "value" };

		@Override
		public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
			String authorities = "ROLE_USER";
			if (map.containsKey(AUTHORITIES)) {
				authorities = asAuthorities(map.get(AUTHORITIES));
			}
			return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
		}

		private String asAuthorities(Object object) {
			List<Object> authorities = new ArrayList<>();
			if (object instanceof Collection) {
				Collection<?> collection = (Collection<?>) object;
				object = collection.toArray(new Object[0]);
			}
			if (ObjectUtils.isArray(object)) {
				Object[] array = (Object[]) object;
				for (Object value : array) {
					if (value instanceof String) {
						authorities.add(value);
					}
					else if (value instanceof Map) {
						authorities.add(asAuthority((Map<?, ?>) value));
					}
					else {
						authorities.add(value);
					}
				}
				return StringUtils.collectionToCommaDelimitedString(authorities);
			}
			return object.toString();
		}

		private Object asAuthority(Map<?, ?> map) {
			if (map.size() == 1) {
				return map.values().iterator().next();
			}
			for (String key : AUTHORITY_KEYS) {
				if (map.containsKey(key)) {
					return map.get(key);
				}
			}
			return map;
		}

	}
}

