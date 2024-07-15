package net.edu.sartuweb.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class WebExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebExceptionHandler.class);
	
	protected static final String FAILURE_MESSAGE = "failureMessage";
	protected static final String ERROR_DEFECTO_APLICACION = "error.aplicacion";
	
	private static final String HOME = "/SartuIntegracionWEB/";
	
//	private static final String PAGINA_ERROR = "/SartuIntegracionWEB/error";
	
	@ExceptionHandler(Exception.class)
	public RedirectView handleException(Exception exception, HttpServletRequest request, HttpSession session){
		LOGGER.error("Se ha producido un error en la aplicación", exception);
		
		RedirectView rw = new RedirectView(HOME);
		FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
		if (outputFlashMap != null){
			outputFlashMap.put(FAILURE_MESSAGE, ERROR_DEFECTO_APLICACION);
		}
		
		return rw;
	}
}
