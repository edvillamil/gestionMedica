package com.consultasmedicas.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.consultasmedicas.app.models.entity.Paciente;

public interface IPacienteDao extends CrudRepository<Paciente, Long>{

	
	@Query("select p from Paciente p join fetch p.usuario u where u.username=?1")
	Paciente findByUsername(String username);

}
