package net.edu.sartuweb.core.daos;

import java.util.List;

import net.edu.sartuweb.core.beans.Usuario;

public interface UsuarioDao {
	
	/**
	 * Obtiene todos los usuarios dados de alta en Sartu
	 * 
	 * @return {@link List<Usuario>} con datos de los usuarios de Sartu
	 */
	List<Usuario> leerUsuarios();
}
