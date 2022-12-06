package com.consultasmedicas.app.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "citas")
@Data
public class Cita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_doctor")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Doctor doctor;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_paciente")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Paciente paciente;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_especialidad")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Especialidad especialidad;
	
	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date fechaCita;
	
	@NotEmpty
	private String horaCita;
	
	private String observaciones;
	
	private Double tarifa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_estado")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Estado estado;
	

	@PrePersist
	public void prePersist() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Date getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(Date fechaCita) {
		this.fechaCita = fechaCita;
	}

	public String getHoraCita() {
		return horaCita;
	}

	public void setHoraCita(String horaCita) {
		this.horaCita = horaCita;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
}
