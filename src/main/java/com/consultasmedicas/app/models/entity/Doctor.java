package com.consultasmedicas.app.models.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "doctores")
@Data
public class Doctor implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_doctor", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombres;

	@NotEmpty
	private String apellidos;
	
	private String direccion;
	
	@NotEmpty
	private String telefono;
	
	@NotEmpty
	private String correo;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "especialidades_doctores",
        joinColumns = @JoinColumn(name = "id_doctor"),
        inverseJoinColumns = @JoinColumn(name = "id_especialidad"))
    private List<Especialidad> especialidades;
	

	@JsonIgnore
	@OneToOne
	@JoinColumn(name="username")
	private Usuario usuario;
	
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "factura_id")
	private List<ItemFactura> items;*/

	public Doctor() {
		//this.items = new ArrayList<ItemFactura>();
	}

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

	public void setNombres(String nombre) {
		this.nombres = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Especialidad> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidad(List<Especialidad> especialidad) {
		this.especialidades = especialidad;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setEspecialidades(List<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}
	
	
	

}
