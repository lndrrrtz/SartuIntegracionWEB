package net.edu.sartuweb.core.facades;

import java.util.List;

import net.edu.sartuweb.core.beans.Restriccion;

public interface RestriccionFacade {
	
	/**
	 * Inserta una restricción
	 * 
	 * @param restriccion Datos de la restricción a insertar
	 * @return Número de registros insertados. 1 = insertado, 0 = no insertado
	 */
	int insertarRestriccion(Restriccion restriccion);
	
	/**
	 * Modifica una restricción
	 * 
	 * @param restriccion Datos de la restricción a insertar
	 * @return Número de registros modificados. 1 = modificado, 0 = no modificado
	 */
	int modificarRestriccion(Restriccion restriccion);
	
	/**
	 * Elimina una restricción
	 * 
	 * @param id Id de la restricción
	 * @return Número de registros eliminados. 1 = eliminado, 0 = no eliminado
	 */
	int eliminarRestriccion(String id);
	
	/**
	 * Obtiene todos de las restricciones dados de alta en Sartu
	 * 
	 * @return {@link List<Restriccion>} con datos de los restricciones de Sartu
	 */
	List<Restriccion> leerRestricciones();
	
	/**
	 * Obtiene los datos de la restricción con el id proporcionado
	 * 
	 * @param id Id del restriccion
	 * @return {@link Restriccion} con los datos de la restricción
	 */
	Restriccion leerRestriccion(String id);
}
 