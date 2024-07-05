package net.edu.sartuweb.web.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import net.edu.sartuweb.core.beans.UsuarioAutenticado;

public class UsuarioArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer container, 
			NativeWebRequest nativeWebRequest, WebDataBinderFactory binderFactory) throws Exception {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().isAssignableFrom(UsuarioAutenticado.class);
	}	
}
