
package net.edu.sartuweb.core.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import net.edu.sartuweb.core.enums.TipoUsuario;

public class Usuario {

	@NotEmpty
	private String id;
	
	@NotEmpty
	private String contrasena;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String dni;
	
	@Email
	@NotEmpty
	private String email;
	
	@NotNull
	private TipoUsuario tipo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

}
