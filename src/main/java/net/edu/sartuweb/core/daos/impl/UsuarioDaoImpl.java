package net.edu.sartuweb.core.daos.impl;

import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import net.edu.sartuweb.core.beans.Usuario;
import net.edu.sartuweb.core.daos.UsuarioDao;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	private static final String ID = "id";
	private static final String CONTRASENA = "contrasena";
	private static final String NOMBRE = "nombre";
	private static final String DNI = "dni";
	private static final String EMAIL = "email";
	private static final String TIPO = "tipo";
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private RowMapper<Usuario> rowMapper = new BeanPropertyRowMapper<>(Usuario.class);
	
	private static final String INSERTARUSUARIO_SQL = StringUtils.join("INSERT INTO ", Tablas.USUARIO, " (ID, CONTRASENA, NOMBRE, DNI, EMAIL, TIPO) VALUES (:", ID, ", :", CONTRASENA, ", :", NOMBRE, ", :", DNI, ", :", EMAIL, ", :", TIPO, ")");
	
	private static final String MODIFICARUSUARIO_SQL = StringUtils.join("UPDATE ", Tablas.USUARIO, " SET CONTRASENA = :", CONTRASENA, ", NOMBRE = :" , NOMBRE, ", DNI = :", DNI, ", EMAIL = :", EMAIL, ", TIPO = :", TIPO, " WHERE ID = :", ID);
	
	private static final String ELIMINARUSUARIO_SQL = StringUtils.join("DELETE FROM ", Tablas.USUARIO, " WHERE ID = :", ID);
	
	private static final String LEERUSUARIOS_SQL = StringUtils.join("SELECT ID, CONTRASENA, NOMBRE, DNI, EMAIL, TIPO FROM ", Tablas.USUARIO);
	
	private static final String LEERUSUARIO_SQL = StringUtils.join("SELECT ID, CONTRASENA, NOMBRE, DNI, EMAIL, TIPO FROM ", Tablas.USUARIO, " WHERE ID = :", ID);
	
	@Override
	public int insertarUsuario(Usuario usuario) {
		BeanPropertySqlParameterSource parametros = new BeanPropertySqlParameterSource(usuario);
		parametros.registerSqlType(TIPO, Types.VARCHAR);
		return namedParameterJdbcTemplate.update(INSERTARUSUARIO_SQL, parametros);
	}
	
	@Override
	public int modificarUsuario(Usuario usuario) {
		BeanPropertySqlParameterSource parametros = new BeanPropertySqlParameterSource(usuario);
		parametros.registerSqlType(TIPO, Types.VARCHAR);
		return namedParameterJdbcTemplate.update(MODIFICARUSUARIO_SQL, parametros);
	}
	
	@Override
	public int eliminarUsuario(String id) {
		Map<String,String> parametro = Collections.singletonMap(ID, id);
		return namedParameterJdbcTemplate.update(ELIMINARUSUARIO_SQL, parametro);
	}
	
	@Override
	public List<Usuario> leerUsuarios() {
		return jdbcTemplate.query(LEERUSUARIOS_SQL, rowMapper);
	}
	
	@Override
	public Usuario leerUsuario(String id) {
		Map<String, String> parametro = Collections.singletonMap(ID, id);
		
		try {
			return namedParameterJdbcTemplate.queryForObject(LEERUSUARIO_SQL, parametro, rowMapper);
		} catch (Exception e) {
			return null;
		}
	}
	
	
}