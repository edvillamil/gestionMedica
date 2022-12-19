package com.consultasmedicas.app.models.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.consultasmedicas.app.models.entity.Cita;

@RunWith(SpringRunner.class)
//@SpringBootTest(properties = "spring.datasource.url=jdbc:mysql://localhost/db_consultasmedicas_pru?useSSL=false&serverTimezone=UTC")
class CitaServiceImplTest {
	
	
	 /**
     * Inyeccion del servicioa probar
     */
	@Autowired
    ICitaService service;

    /**
     * Crear un mock con el repositorio(Dao)
     */
	//@MockBean
    //private ICitaDao citaDao;

	
	@BeforeEach
	void setUp() {
	}

	@Test
	void testFindAll() {
		
    	//when(citaDao.findAll()).thenReturn(this.citas);
    	
    	// Ejecutar la llamada al servicio
        List<Cita> citasa = service.findAll();
    	
		Assertions.assertTrue(!citasa.isEmpty());
		Assertions.assertEquals(2, citasa.size());
	}

	@Test
	void testFindAllPageable() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByPacienteId() {
		fail("Not yet implemented");
	}

	@Test
	void testFindBydoctorId() {
		fail("Not yet implemented");
	}

	@Test
	void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	void testSave() {
		fail("Not yet implemented");
	}

}
