package net.edu.sartuweb.web.controllers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.edu.sartuweb.core.beans.Cliente;
import net.edu.sartuweb.core.facades.ClienteFacade;

/**
 * Clase controladora para gestionar solicitudes relacionadas con los clientes
 */
@RequestMapping("/clientes")
@Controller
public class ClienteController extends ComunController {
	
	// Constantes relacionadas con los tiles
	private static final String CLIENTES_TILES = "clientes/clientes";
	private static final String CLIENTE_TILES = "clientes/cliente";
	private static final String REDIRECT_CLIENTES_TILES = "redirect:/clientes";

	// Constantes relacionadas con los datos
	private static final String CLIENTES_LISTA = "clientes";
	private static final String CLIENTE = "cliente";
	
	
	@Autowired
	private ClienteFacade clienteFacade;
	
	/**
	 * Cargar clientes
	 */
	@RequestMapping()
	public String leerClientes(Model model){
		
		// Obtener todos los clientes de Sartu
		List<Cliente> clientes = clienteFacade.leerClientes();
		
		// Añade la lista de clientes al modelo
		model.addAttribute(CLIENTES_LISTA, clientes);

		// Devuelve el nombre de la vista para su renderización
		return CLIENTES_TILES;
	}

	/**
	 * Cargar formulario para crear nuevo cliente
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String nuevoCliente(Model model){
		model.addAttribute(CLIENTE, new Cliente());
		return CLIENTE_TILES;
	}

	/**
	 * Crear nuevo cliente
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String nuevoCliente(@Validated @ModelAttribute Cliente cliente, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		
		// Comprueba si hay errores de validación
		if (!bindingResult.hasErrors()) {
			
			// Inserta el cliente en la base de datos
			int resultado = clienteFacade.insertarCliente(cliente);
			
			// Añade los literales sobre el resultado del proceso para que sean mostrados en pantalla
			if (resultado > NumberUtils.INTEGER_ZERO) {
				// Mensaje para advertir que el cliente se ha creado
				redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, GUARDADO);
				// Redirecciona a la pantalla de edición de clientes y carga los datos
				return StringUtils.join("redirect:/clientes/", cliente.getClientId(), "/edit");
			}
			else {
				// Mensaje de error para advertir que no se ha guardado
				model.addAttribute(FAILURE_MESSAGE, "cliente.existe");
			}
		}
		
		// Devuelve el nombre de la vista para su renderización
		return CLIENTE_TILES;
	}
	
	/**
	 * Mostrar datos de clientes
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String cargarCliente(@PathVariable String id, Model model) {
		
		// Obtener los datos del cliente
		Cliente cliente = clienteFacade.leerCliente(id);
		
		// Añade los datos del formulario del cliente al modelo
		model.addAttribute(CLIENTE, cliente);

		// Devuelve el nombre de la vista para su renderización
		return CLIENTE_TILES;
	}
	
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
	public String editarCliente(@Validated @ModelAttribute Cliente cliente, BindingResult bindingResult,
			Model model) {
		
		// Comprueba si hay errores de validación
		if (!bindingResult.hasErrors()) {
	
			// Modifica el cliente
			int resultado = clienteFacade.modificarCliente(cliente);
			
			// Añade los literales sobre el resultado del proceso para que sean mostrados en pantalla
			if (resultado > NumberUtils.INTEGER_ZERO) {
				// Mensaje para advertir que el cliente se ha modificado
				model.addAttribute(SUCCESS_MESSAGE, GUARDADO);
			}
			else {
				// Mensaje de error para advertir que no se ha modificado
				model.addAttribute(FAILURE_MESSAGE, "error.modificar");
			}
		}
		
		// Devuelve el nombre de la vista para su renderización
		return CLIENTE_TILES;
	}
	
	/**
	 * Mostrar datos de clientes
	 */
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String eliminarCliente(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
		
		// elimina el cliente
		int resultado = clienteFacade.eliminarCliente(id);
		
		// Añade los literales sobre el resultado del proceso para que sean mostrados en pantalla
		if (resultado > NumberUtils.INTEGER_ZERO) {
			// Mensaje para advertir que el cliente se ha eliminado
			redirectAttributes.addAttribute(SUCCESS_MESSAGE, ELIMINADO);
		}
		else {
			// Mensaje para advertir que el cliente se ha eliminado
			redirectAttributes.addAttribute(FAILURE_MESSAGE, "error.eliminar");
		}
		
		// Devuelve el nombre de la vista para su renderización
		return REDIRECT_CLIENTES_TILES;
	}
}
