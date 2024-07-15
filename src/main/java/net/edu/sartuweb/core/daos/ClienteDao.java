package net.edu.sartuweb.core.daos;

import java.util.List;

import net.edu.sartuweb.core.beans.Cliente;

public interface ClienteDao {
	
	/**
	 * Inserta un cliente
	 * 
	 * @param cliente Datos del cliente a insertar
	 * @return Número de registros insertados. 1 = insertado, 0 = no insertado
	 */
	int insertarCliente(Cliente cliente);
	
	/**
	 * Modifica un cliente
	 * 
	 * @param cliente Datos del cliente a insertar
	 * @return Número de registros modificados. 1 = modificado, 0 = no modificado
	 */
	int modificarCliente(Cliente cliente);
	
	/**
	 * Elimina un cliente
	 * 
	 * @param id Id del cliente
	 * @return Número de registros eliminados. 1 = eliminado, 0 = no eliminado
	 */
	int eliminarCliente(String id);
	
	/**
	 * Obtiene todos los clientes dados de alta en Sartu
	 * 
	 * @return {@link List<Cliente>} con datos de los clientes de Sartu
	 */
	List<Cliente> leerClientes();
	
	/**
	 * Obtiene los datos del cliente con el id proporcionado
	 * 
	 * @param id Id del cliente
	 * @return {@link Cliente} con los datos del cliente
	 */
	Cliente leerCliente(String id);
}
 