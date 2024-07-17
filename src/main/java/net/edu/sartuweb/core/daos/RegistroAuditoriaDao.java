package net.edu.sartuweb.core.daos;

import java.util.List;

import net.edu.sartuweb.core.beans.RegistroAuditoria;

public interface RegistroAuditoriaDao {
	
	/**
	 * Busca registros de acceso
	 * 
	 * @param registroAuditoria Datos de la búsqueda
	 * @return Listado de {@link RegistroAuditoria} con datos de las auditorías
	 */
	List<RegistroAuditoria> buscarRegistrosAuditoria(RegistroAuditoria registroAuditoria);
}
 