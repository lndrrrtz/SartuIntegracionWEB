package net.edu.sartuweb.core.facades.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.edu.sartuweb.core.beans.Usuario;
import net.edu.sartuweb.core.daos.UsuarioDao;
import net.edu.sartuweb.core.facades.UsuarioFacade;

@Service
public class UsuarioFacadeImpl implements UsuarioFacade {

	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	public List<Usuario> leerUsuarios() {
		return usuarioDao.leerUsuarios();
	}
	
}
