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

import net.edu.sartuweb.core.beans.Restriccion;
import net.edu.sartuweb.core.facades.RestriccionFacade;

/**
 * Clase controladora para gestionar solicitudes relacionadas con los restricciones
 */
@RequestMapping("/restricciones")
@Controller
public class RestriccionController extends ComunController {
	
	// Constantes relacionadas con los tiles
	private static final String RESTRICCIONES_TILES = "restricciones/restricciones";
	private static final String RESTRICCION_TILES = "restricciones/restriccion";
	private static final String REDIRECT_RESTRICCIONES_TILES = "redirect:/restricciones";

	// Constantes relacionadas con los datos
	private static final String RESTRICCIONES_LISTA = "restricciones";
	private static final String RESTRICCION = "restriccion";
	
	
	@Autowired
	private RestriccionFacade restriccionFacade;
	
	/**
	 * Cargar restricciones
	 */
	@RequestMapping()
	public String leerRestricciones(Model model){
		
		// Obtener todas las restricciones de Sartu
		List<Restriccion> restricciones = restriccionFacade.leerRestricciones();
		
		// Añade la lista de restricciones al modelo
		model.addAttribute(RESTRICCIONES_LISTA, restricciones);

		// Devuelve el nombre de la vista para su renderización
		return RESTRICCIONES_TILES;
	}

	/**
	 * Cargar formulario para crear nueva restriccion
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String nuevoRestriccion(Model model){
		model.addAttribute(RESTRICCION, new Restriccion());
		return RESTRICCION_TILES;
	}

	/**
	 * Crear nueva restriccion
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String nuevoRestriccion(@Validated @ModelAttribute Restriccion restriccion, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		
		// Comprueba si hay errores de validación
		if (!bindingResult.hasErrors()) {
			
			// Inserta la restriccion en la base de datos
			int resultado = restriccionFacade.insertarRestriccion(restriccion);
			
			// Añade los literales sobre el resultado del proceso para que sean mostrados en pantalla
			if (resultado > NumberUtils.INTEGER_ZERO) {
				// Mensaje para advertir que la restriccion se ha creado
				redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, GUARDADO);
				// Redirecciona a la pantalla de edición de restricciones y carga los datos
				return StringUtils.join("redirect:/restricciones");
			}
			else {
				// Mensaje de error para advertir que no se ha guardado
				model.addAttribute(FAILURE_MESSAGE, "restriccion.existe");
			}
		}
		
		// Devuelve el nombre de la vista para su renderización
		return RESTRICCION_TILES;
	}
	
	/**
	 * Mostrar datos de las restricciones
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String cargarRestriccion(@PathVariable String id, Model model) {
		
		// Obtener los datos de la restricción
		Restriccion restriccion = restriccionFacade.leerRestriccion(id);
		
		// Añade los datos del formulario de´la restriccin al modelo
		model.addAttribute(RESTRICCION, restriccion);

		// Devuelve el nombre de la vista para su renderización
		return RESTRICCION_TILES;
	}
	
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
	public String editarRestriccion(@Validated @ModelAttribute Restriccion restriccion, BindingResult bindingResult,
			Model model) {
		
		// Comprueba si hay errores de validación
		if (!bindingResult.hasErrors()) {
	
			// Modifica la restriccion
			int resultado = restriccionFacade.modificarRestriccion(restriccion);
			
			// Añade los literales sobre el resultado del proceso para que sean mostrados en pantalla
			if (resultado > NumberUtils.INTEGER_ZERO) {
				// Mensaje para advertir que la restriccion se ha modificado
				model.addAttribute(SUCCESS_MESSAGE, GUARDADO);
			}
			else {
				// Mensaje de error para advertir que no se ha modificado
				model.addAttribute(FAILURE_MESSAGE, "error.modificar");
			}
		}
		
		// Devuelve el nombre de la vista para su renderización
		return RESTRICCION_TILES;
	}
	
	/**
	 * Mostrar datos de restricciones
	 */
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String eliminarRestriccion(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
		
		// elimina la restriccion
		int resultado = restriccionFacade.eliminarRestriccion(id);
		
		// Añade los literales sobre el resultado del proceso para que sean mostrados en pantalla
		if (resultado > NumberUtils.INTEGER_ZERO) {
			// Mensaje para advertir que la restriccion se ha eliminado
			redirectAttributes.addAttribute(SUCCESS_MESSAGE, ELIMINADO);
		}
		else {
			// Mensaje para advertir que la restriccion se ha eliminado
			redirectAttributes.addAttribute(FAILURE_MESSAGE, "error.eliminar");
		}
		
		// Devuelve el nombre de la vista para su renderización
		return REDIRECT_RESTRICCIONES_TILES;
	}
}
