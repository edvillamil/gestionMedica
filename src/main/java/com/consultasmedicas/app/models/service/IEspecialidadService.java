package com.consultasmedicas.app.models.service;

import java.util.List;


import com.consultasmedicas.app.models.entity.Especialidad;

public interface IEspecialidadService {

	public List<Especialidad> findAll();
	
	Especialidad findById(Long id);

	void save(Especialidad especialidad);

	void delete(Long id);
	
	boolean hasCitasAsociadas(Long id);
	
	boolean hasDoctoresAsociadas(Long id);

	List<Especialidad> findByNombre(String term);
}
