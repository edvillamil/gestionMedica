package com.consultasmedicas.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "especialidades")
public class Especialidad implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_especialidad")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;

	@JsonIgnore
	@ManyToMany(mappedBy = "especialidades")
    List<Doctor> doctores;
	
	
	
	public List<Doctor> getDoctores() {
		return doctores;
	}

	public void setDoctores(List<Doctor> doctores) {
		this.doctores = doctores;
	}

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	

}
