package com.consultasmedicas.app.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultasmedicas.app.models.dao.ICitaDao;
import com.consultasmedicas.app.models.entity.Cita;


@Service
public class CitaServiceImpl implements ICitaService {

	private ICitaDao citaDao;
	
	public CitaServiceImpl(ICitaDao citaDao) {
        this.citaDao = citaDao;
    }

	@Override
	@Transactional(readOnly = true)
	public List<Cita> findAll() {
		// TODO Auto-generated method stub
		return (List<Cita>) citaDao.findAll();
	}

	@Override
	public Page<Cita> findAll(Pageable pageable) {
		return citaDao.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public List<Cita> findByPacienteId(Long id) {
		// TODO Auto-generated method stub
		return (List<Cita>) citaDao.fetchByIdWithPaciente(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Cita> findBydoctorId(Long id) {
		// TODO Auto-generated method stub
		return (List<Cita>) citaDao.fetchByIdWithdoctor(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cita findById(Long id) {
		// TODO Auto-generated method stub
		return citaDao.findById(id).orElse(null);
	}
	
	@Override
	public void save(Cita cita) {
		citaDao.save(cita);
	}

}
