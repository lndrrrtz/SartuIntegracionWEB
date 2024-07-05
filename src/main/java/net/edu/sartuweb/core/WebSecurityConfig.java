package net.edu.sartuweb.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/recursos/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			.addFilterAfter(oAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
//			.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class).httpBasic()
//			.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().authorizeRequests()
			.authorizeRequests()	
				.antMatchers("/**").permitAll();
//			.antMatchers("/", "/error", "/sesion/error", "/logout").permitAll().antMatchers("/**").authenticated()
//				.and().logout().permitAll().and().csrf().disable();
	}
	
}
