package net.edu.sartuweb.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccesoController {
	
	private static final String INICIO = "inicio";
	
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public String  cargarInicio() {
		return INICIO;
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error() {
		return INICIO;
	}
	
}
