package net.edu.sartuweb.core.facades.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.edu.sartuweb.core.beans.Restriccion;
import net.edu.sartuweb.core.daos.RestriccionDao;
import net.edu.sartuweb.core.facades.RestriccionFacade;

@Service
public class RestriccionFacadeImpl implements RestriccionFacade {

	@Autowired
	private RestriccionDao restriccionDao;
	
	@Override
	public int insertarRestriccion(Restriccion restriccion) {
		return restriccionDao.insertarRestriccion(restriccion);
	}

	@Override
	public int modificarRestriccion(Restriccion restriccion) {
		return restriccionDao.modificarRestriccion(restriccion);
	}

	@Override
	public int eliminarRestriccion(String id) {
		return restriccionDao.eliminarRestriccion(id);
	}

	@Override
	public List<Restriccion> leerRestricciones() {
		return restriccionDao.leerRestricciones();
	}

	@Override
	public Restriccion leerRestriccion(String id) {
		return restriccionDao.leerRestriccion(id);
	}

}
