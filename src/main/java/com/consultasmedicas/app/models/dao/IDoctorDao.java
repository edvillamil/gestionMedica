package com.consultasmedicas.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.consultasmedicas.app.models.entity.Doctor;



public interface IDoctorDao extends CrudRepository<Doctor, Long> {

	@Query("select d from Doctor d join fetch d.usuario u where u.username=?1")
	Doctor findByUsername(String username);
	
	@Query(
			  value = "select d.* from Doctores d inner join especialidades_doctores e on d.id_doctor = e.id_doctor where e.id_especialidad=?1", 
			  nativeQuery = true)
	
	//@Query("select d from Doctor d")
	List<Doctor> findByEspecialidad(Long idEspecialidad);


}

