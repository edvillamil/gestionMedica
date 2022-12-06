package com.consultasmedicas.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultasmedicas.app.models.dao.IEspecialidadDao;
import com.consultasmedicas.app.models.entity.Especialidad;


@Service
public class EspecialidadServiceImpl implements IEspecialidadService {

	
	@Autowired
	IEspecialidadDao especialidadDao;
	
	@Override
	public List<Especialidad> findAll() {
		return (List<Especialidad>) especialidadDao.findAll();
	}

	@Override
	public Especialidad findById(Long id) {		
		return especialidadDao.findById(id).orElse(null);
	}

	@Override
	public void save(Especialidad especialidad) {
		especialidadDao.save(especialidad);
	}

	@Override
	public void delete(Long id) {
		especialidadDao.deleteById(id);	
	}

	@Override
	public boolean hasCitasAsociadas(Long id) {
		return especialidadDao.hasCitasAsociadas(id) > 0;
	}

	@Override
	public boolean hasDoctoresAsociadas(Long id) {
		return especialidadDao.hasDoctoresAsociadas(id) > 0;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Especialidad> findByNombre(String term) {
		return especialidadDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

}
