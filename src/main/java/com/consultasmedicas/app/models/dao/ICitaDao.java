package com.consultasmedicas.app.models.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.consultasmedicas.app.models.entity.Cita;



public interface ICitaDao extends PagingAndSortingRepository<Cita, Long> {

	@Query("select c from Cita c join fetch c.paciente p where p.id=?1")
	public List<Cita> fetchByIdWithPaciente(Long id);
	
	@Query("select c from Cita c join fetch c.doctor d where d.id=?1")
	public List<Cita> fetchByIdWithdoctor(Long id);
	
}