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

import net.edu.sartuweb.core.beans.Restriccion;
import net.edu.sartuweb.core.daos.RestriccionDao;

@Repository
public class RectriccionDaoImpl implements RestriccionDao {

	private static final String ID = "id";
	private static final String TIPO = "tipo";
	private static final String ACCION = "accion";
	private static final String DATOS = "datos";
	private static final String DESCRIPCION = "descripcion";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private RowMapper<Restriccion> rowMapper = new BeanPropertyRowMapper<>(Restriccion.class);
	
	private static final String INSERTARRESTRICCION_SQL = StringUtils.join("INSERT INTO ", Tablas.RESTRICCION, " (TIPO, ACCION, DATOS, DESCRIPCION) VALUES (:", TIPO, ", :", ACCION, ", :", DATOS, ", :", DESCRIPCION, ")");
	
	private static final String MODIFICARRESTRICCION_SQL = StringUtils.join("UPDATE ", Tablas.RESTRICCION, " SET TIPO = :", TIPO, ", ACCION = :" , ACCION, ", DATOS = :", DATOS, ", DESCRIPCION = :", DESCRIPCION, " WHERE ID = :", ID);
	
	private static final String ELIMINARRESTRICCION_SQL = StringUtils.join("DELETE FROM ", Tablas.RESTRICCION, " WHERE ID = :", ID);
	
	private static final String LEERRESTRICCIONES_SQL = StringUtils.join("SELECT ID, TIPO, ACCION, DATOS, DESCRIPCION FROM " + Tablas.RESTRICCION);
	
	private static final String LEERRESTRICCION_SQL = StringUtils.join(LEERRESTRICCIONES_SQL + " WHERE ID = :", ID);
	
	@Override
	public int insertarRestriccion(Restriccion restriccion) {
		BeanPropertySqlParameterSource parametros = new BeanPropertySqlParameterSource(restriccion);
		parametros.registerSqlType(TIPO, Types.VARCHAR);
		parametros.registerSqlType(ACCION, Types.VARCHAR);
		return namedParameterJdbcTemplate.update(INSERTARRESTRICCION_SQL, parametros);
	}
	
	@Override
	public int modificarRestriccion(Restriccion restriccion) {
		BeanPropertySqlParameterSource parametros = new BeanPropertySqlParameterSource(restriccion);
		parametros.registerSqlType(TIPO, Types.VARCHAR);
		parametros.registerSqlType(ACCION, Types.VARCHAR);
		return namedParameterJdbcTemplate.update(MODIFICARRESTRICCION_SQL, parametros);
	}
	
	@Override
	public int eliminarRestriccion(String id) {
		Map<String,String> parametro = Collections.singletonMap(ID, id);
		return namedParameterJdbcTemplate.update(ELIMINARRESTRICCION_SQL, parametro);
	}
	
	@Override
	public List<Restriccion> leerRestricciones() {
		return jdbcTemplate.query(LEERRESTRICCIONES_SQL, rowMapper);
	}
	
	@Override
	public Restriccion leerRestriccion(String id) {
		Map<String, String> parametro = Collections.singletonMap(ID, id);
		
		try {
			return namedParameterJdbcTemplate.queryForObject(LEERRESTRICCION_SQL, parametro, rowMapper);
		} catch (Exception e) {
			return null;
		}
	}
}