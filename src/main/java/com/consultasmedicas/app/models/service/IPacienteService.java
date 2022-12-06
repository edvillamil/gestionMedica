package com.consultasmedicas.app.models.service;

import java.util.List;

import com.consultasmedicas.app.models.entity.Paciente;

public interface IPacienteService {
	
	public List<Paciente> findAll();

	Paciente findPacienteByUsername(String usernmae);
	
	Paciente findById(Long id);
	
	void save(Paciente paciente);
	
	void delete(Long id);

}
