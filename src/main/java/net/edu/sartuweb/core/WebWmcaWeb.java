package net.edu.sartuweb.core;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import net.edu.sartuweb.web.security.UsuarioArgumentResolver;

@EnableWebMvc
@ComponentScan({ "net.edu.sartuweb.web.controllers" })
@Import({ WebCore.class })
public class WebWmcaWeb extends WebMvcConfigurerAdapter {

	public static final Locale LOCALE_ES = new Locale("es", "ES");
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/recursos/**").addResourceLocations("/recursos/");
	}

	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new UsuarioArgumentResolver());
	}
	
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addViewController("/accessDenied").setViewName("restringido/accessDenied");
	}
	
	@Bean
	public TilesViewResolver tilesViewResolver() {
		final TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		tilesViewResolver.setRequestContextAttribute("springRequestContext");
		return tilesViewResolver;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] { "/WEB-INF/tiles-defs.xml" });
		return tilesConfigurer;
	}
	
	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(LOCALE_ES);
		return localeResolver;
	}
	
	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("net/edu/sartuweb/web/resources/ApplicationResources");
		source.setUseCodeAsDefaultMessage(Boolean.TRUE);
		source.setDefaultEncoding("UTF-8");
		return source;
	}
	
	@Bean(name = "messageAccessor")
	public MessageSourceAccessor messageSourceAccessor() {
		return new MessageSourceAccessor(this.messageSource());
	}
	
}
