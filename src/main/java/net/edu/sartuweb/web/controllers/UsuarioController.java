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

import net.edu.sartuweb.core.beans.Usuario;
import net.edu.sartuweb.core.facades.UsuarioFacade;

/**
 * Clase controladora para gestionar solicitudes relacionadas con los usuarios
 */
@RequestMapping("/usuarios")
@Controller
public class UsuarioController extends ComunController {
	
	// Constantes relacionadas con los tiles
	private static final String USUARIOS_TILES = "usuarios/usuarios";
	private static final String USUARIO_TILES = "usuarios/usuario";
	private static final String REDIRECT_USUARIOS_TILES = "redirect:/usuarios";

	// Constantes relacionadas con los datos
	private static final String USUARIOS_LISTA = "usuarios";
	private static final String USUARIO = "usuario";
	
	
	@Autowired
	private UsuarioFacade usuarioFacade;
	
	/**
	 * Cargar usuarios
	 */
	@RequestMapping()
	public String leerUsuarios(Model model){
		
		// Obtener todos los usuarios de Sartu
		List<Usuario> usuarios = usuarioFacade.leerUsuarios();
		// Añade la lista de usuarios al modelo
		model.addAttribute(USUARIOS_LISTA, usuarios);

		// Devuelve el nombre de la vista para su renderización
		return USUARIOS_TILES;
	}

	/**
	 * Cargar formulario para crear nuevo usuario
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String nuevoUsuario(Model model){
		model.addAttribute(USUARIO, new Usuario());
		return USUARIO_TILES;
	}

	/**
	 * Crear nuevo usuario
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String nuevoUsuario(@Validated @ModelAttribute Usuario usuario, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		
		// Comprueba si hay errores de validación
		if (!bindingResult.hasErrors()) {
			
			// Inserta el usuario en la base de datos
			int resultado = usuarioFacade.insertarUsuario(usuario);
			
			// Añade los literales sobre el resultado del proceso para que sean mostrados en pantalla
			if (resultado > NumberUtils.INTEGER_ZERO) {
				// Mensaje para advertir que el usuario se ha creado
				redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, GUARDADO);
				// Redirecciona a la pantalla de edición de usuarios y carga los datos
				return StringUtils.join("redirect:/usuarios/", usuario.getId(), "/edit");
			}
			else {
				// Mensaje de error para advertir que no se ha guardado
				model.addAttribute(FAILURE_MESSAGE, "usuario.existe");
			}
		}
		
		// Devuelve el nombre de la vista para su renderización
		return USUARIO_TILES;
	}
	
	/**
	 * Mostrar datos de usuarios
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String cargarUsuario(@PathVariable String id, Model model) {
		
		// Obtener los datos del usuario
		Usuario usuario = usuarioFacade.leerUsuario(id);
		
		// Añade los datos del formulario del usuario al modelo
		model.addAttribute(USUARIO, usuario);

		// Devuelve el nombre de la vista para su renderización
		return USUARIO_TILES;
	}
	
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
	public String editarUsuario(@Validated @ModelAttribute Usuario usuario, BindingResult bindingResult,
			Model model) {
		
		// Comprueba si hay errores de validación
		if (!bindingResult.hasErrors()) {
	
			// Modifica el usuario
			int resultado = usuarioFacade.modificarUsuario(usuario);
			
			// Añade los literales sobre el resultado del proceso para que sean mostrados en pantalla
			if (resultado > NumberUtils.INTEGER_ZERO) {
				// Mensaje para advertir que el usuario se ha modificado
				model.addAttribute(SUCCESS_MESSAGE, GUARDADO);
			}
			else {
				// Mensaje de error para advertir que no se ha modificado
				model.addAttribute(FAILURE_MESSAGE, "error.modificar");
			}
		}
		
		// Devuelve el nombre de la vista para su renderización
		return USUARIO_TILES;
	}
	
	/**
	 * Mostrar datos de usuarios
	 */
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String eliminarUsuario(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
		
		// elimina el usuario
		int resultado = usuarioFacade.eliminarUsuario(id);
		
		// Añade los literales sobre el resultado del proceso para que sean mostrados en pantalla
		if (resultado > NumberUtils.INTEGER_ZERO) {
			// Mensaje para advertir que el usuario se ha eliminado
			redirectAttributes.addAttribute(SUCCESS_MESSAGE, ELIMINADO);
		}
		else {
			// Mensaje para advertir que el usuario se ha eliminado
			redirectAttributes.addAttribute(FAILURE_MESSAGE, "error.eliminar");
		}
		
		// Devuelve el nombre de la vista para su renderización
		return REDIRECT_USUARIOS_TILES;
	}
}
