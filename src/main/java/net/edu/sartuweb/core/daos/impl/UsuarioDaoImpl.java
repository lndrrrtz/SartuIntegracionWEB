package net.edu.sartuweb.core.daos.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import net.edu.sartuweb.core.beans.Usuario;
import net.edu.sartuweb.core.daos.UsuarioDao;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Usuario> rowMapper = new BeanPropertyRowMapper<>(Usuario.class);
	
	private static final String LEERUSUARIO_SQL = "SELECT ID, CONTRASENA, NOMBRE, DNI, EMAIL, TIPO FROM " + Tablas.USUARIO;
	
	@Override
	public List<Usuario> leerUsuarios() {
		return jdbcTemplate.query(LEERUSUARIO_SQL, rowMapper);
	}
}