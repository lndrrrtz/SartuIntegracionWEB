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
	public int insertarUsuario(Usuario usuario) {
		return usuarioDao.insertarUsuario(usuario);
	}

	@Override
	public int modificarUsuario(Usuario usuario) {
		return usuarioDao.modificarUsuario(usuario);
	}

	@Override
	public int eliminarUsuario(String id) {
		return usuarioDao.eliminarUsuario(id);
	}

	@Override
	public List<Usuario> leerUsuarios() {
		return usuarioDao.leerUsuarios();
	}

	@Override
	public Usuario leerUsuario(String id) {
		return usuarioDao.leerUsuario(id);
	}
	
}
