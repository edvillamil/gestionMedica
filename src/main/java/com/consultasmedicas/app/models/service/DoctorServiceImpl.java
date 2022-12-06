package com.consultasmedicas.app.models.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consultasmedicas.app.models.dao.IDoctorDao;
import com.consultasmedicas.app.models.entity.Doctor;

@Service
public class DoctorServiceImpl implements IDoctorService{
	
	@Autowired
	private IDoctorDao doctorDao;

	@Override
	public List<Doctor> findAll() {
		return (List<Doctor>)doctorDao.findAll();
	}
	
	@Override
	public Doctor findDoctorByUsername(String username) {
		return doctorDao.findByUsername(username);
	}

	@Override
	public Doctor findById(Long id) {
		return doctorDao.findById(id).orElse(null);
	}

	@Override
	public void save(@Valid Doctor doctor) {
		doctorDao.save(doctor);	
	}
	
	@Override
	public void delete(Long id) {
		doctorDao.deleteById(id);	
	}

	@Override
	public List<Doctor> findByEspecialidad(Long idEspecialidad) {
		return doctorDao.findByEspecialidad(idEspecialidad);
	}

}
