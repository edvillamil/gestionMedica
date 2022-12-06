package com.consultasmedicas.app.models.service;

import java.util.List;

import javax.validation.Valid;

import com.consultasmedicas.app.models.entity.Doctor;


public interface IDoctorService {
	
	public List<Doctor> findAll();

	Doctor findDoctorByUsername(String username);
	
	Doctor findById(Long id);

	void save(@Valid Doctor doctor);

	void delete(Long id);
	
	List<Doctor> findByEspecialidad(Long idEspecialidad);

}
