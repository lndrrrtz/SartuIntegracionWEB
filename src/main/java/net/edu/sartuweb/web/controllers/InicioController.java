package net.edu.sartuweb.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/inicio")
@Controller
public class InicioController {
	
	private static final String INICIO = "inicio";
	
	@RequestMapping(method = RequestMethod.GET)
	public String  cargarInicio() {
		return INICIO;
	}
}
