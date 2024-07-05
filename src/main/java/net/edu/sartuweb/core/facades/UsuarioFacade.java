package net.edu.sartuweb.core.facades;

import java.util.List;

import net.edu.sartuweb.core.beans.Usuario;

public interface UsuarioFacade {
	
	/**
	 * Obtiene todos los usuarios dados de alta en Sartu
	 * 
	 * @return {@link List<Usuario>} con datos de los usuarios de Sartu
	 */
	List<Usuario> leerUsuarios();
}
