package com.consultasmedicas.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.consultasmedicas.app.models.entity.Paciente;

@XmlRootElement(name="clientesList")
public class ClienteList {
	
	@XmlElement(name="cliente")
	public List<Paciente> pacientes;

	public ClienteList(List<Paciente> pacientes) {
		super();
		this.pacientes = pacientes;
	}
	
	public ClienteList() {
	}

	public List<Paciente> getClientes() {
		return pacientes;
	}
	 
	
}
