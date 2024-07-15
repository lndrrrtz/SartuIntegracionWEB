package net.edu.sartuweb.core.daos;

import java.util.List;

import net.edu.sartuweb.core.beans.Usuario;

public interface UsuarioDao {
	
	/**
	 * Inserta un usuario
	 * 
	 * @param usuario Datos del usuario a insertar
	 * @return Número de registros insertados. 1 = insertado, 0 = no insertado
	 */
	int insertarUsuario(Usuario usuario);
	
	/**
	 * Modifica un usuario
	 * 
	 * @param usuario Datos del usuario a insertar
	 * @return Número de registros modificados. 1 = modificado, 0 = no modificado
	 */
	int modificarUsuario(Usuario usuario);
	
	/**
	 * Elimina un usuario
	 * 
	 * @param id Id del usuario
	 * @return Número de registros eliminados. 1 = eliminado, 0 = no eliminado
	 */
	int eliminarUsuario(String id);
	
	/**
	 * Obtiene todos los usuarios dados de alta en Sartu
	 * 
	 * @return {@link List<Usuario>} con datos de los usuarios de Sartu
	 */
	List<Usuario> leerUsuarios();
	
	/**
	 * Obtiene los datos del usuario con el id proporcionado
	 * 
	 * @param id Id del usuario
	 * @return {@link Usuario} con los datos del usuario
	 */
	Usuario leerUsuario(String id);
}
 