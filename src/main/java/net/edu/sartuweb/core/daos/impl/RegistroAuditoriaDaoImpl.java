package net.edu.sartuweb.core.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import net.edu.sartuweb.core.beans.RegistroAuditoria;
import net.edu.sartuweb.core.daos.RegistroAuditoriaDao;

@Repository
public class RegistroAuditoriaDaoImpl implements RegistroAuditoriaDao {

	private static final String ID = "id";
	private static final String IP = "ip";
	private static final String FLUJO = "flujo";
	private static final String CLIENTID = "clientId";
	private static final String IDUSUARIO = "idUsuario";
	private static final String RESULTADO = "resultado";
	private static final String FECHA = "fecha";
	private static final String SCOPE = "scope";
	
	private static final String AND = " AND ";
	private static final String LIKE = " LIKE :";
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private RowMapper<RegistroAuditoria> rowMapper = new BeanPropertyRowMapper<>(RegistroAuditoria.class);
	
	private static final String LEERREGISTROSAUDITORIA_SQL = StringUtils.join("SELECT ID, IP, FLUJO, CLIENTID, IDUSUARIO, RESULTADO, FECHA, SCOPE FROM ", Tablas.REGISTROAUDITORIA);
	
	@Override
	public List<RegistroAuditoria> buscarRegistrosAuditoria(RegistroAuditoria registroAuditoria) {
		
		Map<String, String> parametros = new HashMap<>();
		
		StringBuilder sql_where = new StringBuilder();
		
		if (registroAuditoria.getId() != null) {
			sql_where.append(AND).append(ID).append(LIKE).append(ID);
			parametros.put(ID, StringUtils.join(registroAuditoria.getId()));
		}
		
		if (StringUtils.isNotBlank(registroAuditoria.getIp())) {
			sql_where.append(AND).append(IP).append(LIKE).append(IP);
			parametros.put(IP, StringUtils.join("%", registroAuditoria.getIp(), "%"));
		}
		
		if (StringUtils.isNotBlank(registroAuditoria.getFlujo())) {
			sql_where.append(AND).append(FLUJO).append(LIKE).append(FLUJO);
			sql_where.append(" AND  FLUJO LIKE :").append(FLUJO);
			parametros.put(FLUJO, StringUtils.join("%", registroAuditoria.getFlujo(), "%"));
		}
		
		if (StringUtils.isNotBlank(registroAuditoria.getClientId())) {
			sql_where.append(AND).append(CLIENTID).append(LIKE).append(CLIENTID);
			parametros.put(CLIENTID, StringUtils.join("%", registroAuditoria.getFlujo(), "%"));
		}
		
		if (StringUtils.isNotBlank(registroAuditoria.getIdUsuario())) {
			sql_where.append(AND).append(IDUSUARIO).append(LIKE).append(IDUSUARIO);
			parametros.put(IDUSUARIO, StringUtils.join("%", registroAuditoria.getIdUsuario(), "%"));
		}
		
		if (StringUtils.isNotBlank(registroAuditoria.getResultado())) {
			sql_where.append(AND).append(RESULTADO).append(LIKE).append(RESULTADO);
			parametros.put(RESULTADO, StringUtils.join("%", registroAuditoria.getResultado(), "%"));
		}
		
		if (registroAuditoria.getFecha() != null) {
			sql_where.append(AND).append(FECHA).append(LIKE).append(FECHA);
			parametros.put(FECHA, StringUtils.join("%", registroAuditoria.getFecha(), "%"));
		}
		
		if (StringUtils.isNotBlank(registroAuditoria.getScope())) {
			sql_where.append(AND).append(SCOPE).append(LIKE).append(SCOPE);
			parametros.put(SCOPE, StringUtils.join("%", registroAuditoria.getScope(), "%"));
		}
		
		String sql = StringUtils.join(LEERREGISTROSAUDITORIA_SQL, sql_where.toString().replaceFirst("AND", "WHERE"));
			
		return namedParameterJdbcTemplate.query(sql, parametros, rowMapper);
	}
}