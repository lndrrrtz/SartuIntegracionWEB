
package net.edu.sartuweb.core.beans;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import net.edu.sartuweb.core.enums.AccionRestriccion;
import net.edu.sartuweb.core.enums.TipoRestriccion;

public class Restriccion implements Serializable {

	private static final long serialVersionUID = 6139359607623497240L;

	private Integer id;

	@NotNull
	private TipoRestriccion tipo;
	
	@NotNull
	private AccionRestriccion accion;
	
	@NotEmpty
	private String datos;
	
	@NotEmpty
	private String descripcion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoRestriccion getTipo() {
		return tipo;
	}

	public void setTipo(TipoRestriccion tipo) {
		this.tipo = tipo;
	}

	public AccionRestriccion getAccion() {
		return accion;
	}

	public void setAccion(AccionRestriccion accion) {
		this.accion = accion;
	}

	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
