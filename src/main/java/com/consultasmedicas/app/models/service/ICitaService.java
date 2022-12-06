package com.consultasmedicas.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.consultasmedicas.app.models.entity.Cita;

public interface ICitaService {
	
	public List<Cita> findAll();
	
	public Page<Cita> findAll(Pageable pageable);

	public List<Cita> findByPacienteId(Long id);

	public List<Cita> findBydoctorId(Long id);
	
	public Cita findById(Long id);

	void save(Cita cita);

}
