package net.edu.sartuweb.core.facades.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.edu.sartuweb.core.beans.Cliente;
import net.edu.sartuweb.core.daos.ClienteDao;
import net.edu.sartuweb.core.facades.ClienteFacade;

@Service
public class ClienteFacadeImpl implements ClienteFacade {

	@Autowired
	private ClienteDao clienteDao;

	@Override
	public int insertarCliente(Cliente cliente) {
		return clienteDao.insertarCliente(cliente);
	}

	@Override
	public int modificarCliente(Cliente cliente) {
		return clienteDao.modificarCliente(cliente);
	}

	@Override
	public int eliminarCliente(String id) {
		return clienteDao.eliminarCliente(id);
	}

	@Override
	public List<Cliente> leerClientes() {
		return clienteDao.leerClientes();
	}

	@Override
	public Cliente leerCliente(String id) {
		return clienteDao.leerCliente(id);
	}
	
}
