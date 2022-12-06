package com.consultasmedicas.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consultasmedicas.app.models.dao.IPacienteDao;
import com.consultasmedicas.app.models.entity.Paciente;

@Service
public class PacienteServiceImpl implements IPacienteService {
	
	@Autowired
	private IPacienteDao pacienteDao;

	@Override
	public List<Paciente> findAll() {
		return (List<Paciente>)pacienteDao.findAll();
	}
	
	@Override
	public Paciente findPacienteByUsername(String username) {
		return pacienteDao.findByUsername(username);
	}

	@Override
	public Paciente findById(Long id) {		
		return pacienteDao.findById(id).orElse(null);
	}

	@Override
	public void save(Paciente paciente) {
		pacienteDao.save(paciente);
	}

	@Override
	public void delete(Long id) {
		pacienteDao.deleteById(id);
	}
	

}
