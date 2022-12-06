package com.consultasmedicas.app.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "pacientes")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String identificacion;
	private String nombres;
	private String apellidos;
	private String direccion;
	private String ciudad;
	private String genero;
	private String correo;
	private String telefonos;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;
	
	@OneToOne
	@JoinColumn(name="username")
	private Usuario usuario;
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}




	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	
	
}
