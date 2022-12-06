package com.consultasmedicas.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.consultasmedicas.app.models.entity.Especialidad;

public interface IEspecialidadDao extends CrudRepository<Especialidad, Long> {
	
	@Query("select count(c) from Cita c where c.especialidad.id=?1")
	int hasCitasAsociadas(Long id);
	
	@Query("select count(d) from Doctor d where d.id=?1")
	int hasDoctoresAsociadas(Long id);
	
	List<Especialidad> findByNombreLikeIgnoreCase(String term);
	
}
