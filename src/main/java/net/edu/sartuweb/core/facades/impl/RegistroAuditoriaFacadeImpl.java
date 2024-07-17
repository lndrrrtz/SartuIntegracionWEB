package net.edu.sartuweb.core.facades.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.edu.sartuweb.core.beans.RegistroAuditoria;
import net.edu.sartuweb.core.daos.RegistroAuditoriaDao;
import net.edu.sartuweb.core.facades.RegistroAuditoriaFacade;

@Service
public class RegistroAuditoriaFacadeImpl implements RegistroAuditoriaFacade {

	@Autowired
	private RegistroAuditoriaDao registroAuditoriaDao;
	
	@Override
	public List<RegistroAuditoria> buscarRegistrosAuditoria(RegistroAuditoria registroAuditoria) {
		return registroAuditoriaDao.buscarRegistrosAuditoria(registroAuditoria);
	}

	
}
