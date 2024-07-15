package net.edu.sartuweb.core.daos.impl;

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

import net.edu.sartuweb.core.beans.Cliente;
import net.edu.sartuweb.core.daos.ClienteDao;

@Repository
public class ClienteDaoImpl implements ClienteDao {

	private static final String CLIENTID = "clientId";
	private static final String CLIENTSECRET = "clientSecret";
	private static final String RESOURCEIDS = "resourceIds";
	private static final String SCOPE = "scope";
	private static final String AUTHORIZEDGRANTTYPES = "authorizedGrantTypes";
	private static final String REGISTEREDREDIRECTURIS = "registeredRedirectUris";
	private static final String AUTHORITIES = "authorities";
	private static final String ACCESSTOKENVALIDITYSECONDS = "accessTokenValiditySeconds";
	private static final String REFRESHTOKENVALIDITYSECONDS = "refreshTokenValiditySeconds";
	private static final String ADDITIONALINFORMATION = "additionalInformation";
	private static final String AUTOAPPROVESCOPES = "autoApproveScopes";
	private static final String DESCRIPCION = "descripcion";
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private RowMapper<Cliente> rowMapper = new BeanPropertyRowMapper<>(Cliente.class);
	
	private static final String INSERTARCLIENTE_SQL = StringUtils.join("INSERT INTO ", Tablas.CLIENTE, " (CLIENTID, CLIENTSECRET, RESOURCEIDS, SCOPE, AUTHORIZEDGRANTTYPES, REGISTEREDREDIRECTURIS, AUTHORITIES, ACCESSTOKENVALIDITYSECONDS, REFRESHTOKENVALIDITYSECONDS, ADDITIONALINFORMATION, AUTOAPPROVESCOPES, DESCRIPCION) VALUES (:", CLIENTID, ", :", CLIENTSECRET, ", :", RESOURCEIDS, ", :", SCOPE, ", :", AUTHORIZEDGRANTTYPES, ", :", REGISTEREDREDIRECTURIS, ", :", AUTHORITIES , ", :", ACCESSTOKENVALIDITYSECONDS , ", :", REFRESHTOKENVALIDITYSECONDS , ", :", ADDITIONALINFORMATION, ", :", AUTOAPPROVESCOPES, ", :",DESCRIPCION, ")");
	
	private static final String MODIFICARCLIENTE_SQL = StringUtils.join("UPDATE ", Tablas.CLIENTE, " SET CLIENTSECRET = :", CLIENTSECRET, ", RESOURCEIDS = :" , RESOURCEIDS, ", SCOPE = :", SCOPE, ", AUTHORIZEDGRANTTYPES = :", AUTHORIZEDGRANTTYPES, ", REGISTEREDREDIRECTURIS = :", REGISTEREDREDIRECTURIS, ", AUTHORITIES = :", AUTHORITIES, ", ACCESSTOKENVALIDITYSECONDS = :", ACCESSTOKENVALIDITYSECONDS, ", REFRESHTOKENVALIDITYSECONDS = :", REFRESHTOKENVALIDITYSECONDS, ", ADDITIONALINFORMATION = :", ADDITIONALINFORMATION, ", AUTOAPPROVESCOPES = :", AUTOAPPROVESCOPES, ", DESCRIPCION = :", DESCRIPCION, " WHERE CLIENTID = :", CLIENTID);
	
	private static final String ELIMINARCLIENTE_SQL = StringUtils.join("DELETE FROM ", Tablas.CLIENTE, " WHERE CLIENTID = :", CLIENTID);
	
	private static final String LEERCLIENTES_SQL = StringUtils.join("SELECT CLIENTID, CLIENTSECRET, RESOURCEIDS, SCOPE, AUTHORIZEDGRANTTYPES, REGISTEREDREDIRECTURIS, AUTHORITIES, ACCESSTOKENVALIDITYSECONDS, REFRESHTOKENVALIDITYSECONDS, ADDITIONALINFORMATION, AUTOAPPROVESCOPES, DESCRIPCION FROM ", Tablas.CLIENTE);
	
	private static final String LEERCLIENTE_SQL = StringUtils.join("SELECT CLIENTID, CLIENTSECRET, RESOURCEIDS, SCOPE, AUTHORIZEDGRANTTYPES, REGISTEREDREDIRECTURIS, AUTHORITIES, ACCESSTOKENVALIDITYSECONDS, REFRESHTOKENVALIDITYSECONDS, ADDITIONALINFORMATION, AUTOAPPROVESCOPES, DESCRIPCION FROM ", Tablas.CLIENTE, " WHERE CLIENTID = :", CLIENTID);
	
	@Override
	public int insertarCliente(Cliente cliente) {
		BeanPropertySqlParameterSource parametros = new BeanPropertySqlParameterSource(cliente);
		return namedParameterJdbcTemplate.update(INSERTARCLIENTE_SQL, parametros);
	}
	
	@Override
	public int modificarCliente(Cliente cliente) {
		BeanPropertySqlParameterSource parametros = new BeanPropertySqlParameterSource(cliente);
		return namedParameterJdbcTemplate.update(MODIFICARCLIENTE_SQL, parametros);
	}
	
	@Override
	public int eliminarCliente(String id) {
		Map<String,String> parametro = Collections.singletonMap(CLIENTID, id);
		return namedParameterJdbcTemplate.update(ELIMINARCLIENTE_SQL, parametro);
	}
	
	@Override
	public List<Cliente> leerClientes() {
		return jdbcTemplate.query(LEERCLIENTES_SQL, rowMapper);
	}
	
	@Override
	public Cliente leerCliente(String id) {
		Map<String, String> parametro = Collections.singletonMap(CLIENTID, id);
		
		try {
			return namedParameterJdbcTemplate.queryForObject(LEERCLIENTE_SQL, parametro, rowMapper);
		} catch (Exception e) {
			return null;
		}
	}
	
	
}