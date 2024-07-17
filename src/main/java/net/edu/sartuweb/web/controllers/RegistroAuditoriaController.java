package net.edu.sartuweb.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.edu.sartuweb.core.beans.RegistroAuditoria;
import net.edu.sartuweb.core.facades.RegistroAuditoriaFacade;

/**
 * Clase controladora para gestionar solicitudes relacionadas con los registros de auditoria
 */
@RequestMapping("/accesos")
@Controller
public class RegistroAuditoriaController extends ComunController {
	
	// Constantes relacionadas con los tiles
	private static final String ACCESOS_TILES = "accesos/accesos";

	// Constantes relacionadas con los datos
	private static final String ACCESO = "acceso";
	private static final String ACCESOS_LISTA = "accesos";
	
	
	@Autowired
	private RegistroAuditoriaFacade registroAuditoriaFacade;
	
	/**
	 * Cargar clientes
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String cargarRegistrosAuditoria(Model model){
		
		RegistroAuditoria registroAuditoria = new RegistroAuditoria();
		
		// Buscar los accesos a Sartu
		List<RegistroAuditoria> registrosAuditoria = registroAuditoriaFacade.buscarRegistrosAuditoria(registroAuditoria);
		
		// Añade la lista de accesos al modelo
		model.addAttribute(ACCESO, new RegistroAuditoria());
		
		// Añade la lista de accesos al modelo
		model.addAttribute(ACCESOS_LISTA, registrosAuditoria);

		// Devuelve el nombre de la vista para su renderización
		return ACCESOS_TILES;
	}
	
	/**
	 * Crear nuevo cliente
	 */
	@RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public String buscarRegistrosAuditoria(@ModelAttribute("acceso") RegistroAuditoria registroAuditoria, Model model) {
		
		// Buscar los accesos a Sartu
		List<RegistroAuditoria> registrosAuditoria = registroAuditoriaFacade.buscarRegistrosAuditoria(registroAuditoria);
		
		// Añade la lista de accesos al modelo
		model.addAttribute(ACCESOS_LISTA, registrosAuditoria);

		// Devuelve el nombre de la vista para su renderización
		return ACCESOS_TILES;
	}
	
}
