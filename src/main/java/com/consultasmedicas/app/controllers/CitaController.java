package com.consultasmedicas.app.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.consultasmedicas.app.models.entity.Cita;
import com.consultasmedicas.app.models.entity.Doctor;
import com.consultasmedicas.app.models.entity.Especialidad;
import com.consultasmedicas.app.models.entity.Estado;
import com.consultasmedicas.app.models.entity.Paciente;
import com.consultasmedicas.app.models.service.ICitaService;
import com.consultasmedicas.app.models.service.IDoctorService;
import com.consultasmedicas.app.models.service.IEspecialidadService;
import com.consultasmedicas.app.models.service.IPacienteService;


@SessionAttributes("cita")
@Controller
@RequestMapping("/citas")
public class CitaController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private ICitaService citaService;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private IDoctorService doctorService;
	
	@Autowired
	private IEspecialidadService especialidadService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = {"/listar"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request,
			Locale locale) {
		
		Paciente paciente = new Paciente();
		Doctor doctor = new Doctor();
		List<Cita> citas = new ArrayList<>();

		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
		}
		
		if(hasRole("ROLE_PACIENTE")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
			paciente = pacienteService.findPacienteByUsername(auth.getName());
			citas = citaService.findByPacienteId(paciente.getId());
			
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
			citas = citaService.findAll();			
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		if(hasRole("ROLE_DOCTOR")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
			doctor = doctorService.findDoctorByUsername(auth.getName());
			citas = citaService.findBydoctorId(doctor.getId());
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");
		
		if(securityContext.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}

		if(request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}	
		
		//Pageable pageRequest = PageRequest.of(page, 4);

		//Page<Cita> citas = citaService.findAll(pageRequest);

		//PageRender<Cita> pageRender = new PageRender<Cita>("/listarCitas", citas);
		
		
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("citas", citas);
		//model.addAttribute("page", pageRender);
		return "citas/listar";
	}
	
	@Secured("ROLE_PACIENTE")
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model, Locale locale) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Paciente paciente = null;
		
		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
			paciente = pacienteService.findPacienteByUsername(auth.getName());		
		}
		
		Cita cita = new Cita();
		cita.setPaciente(paciente);
		cita.setEstado(new Estado(1L));
		
		model.put("listaEspecialidades", especialidadService.findAll());
		
		model.put("cita", cita);
		model.put("titulo", messageSource.getMessage("text.cita.form.titulo.crear", null, locale));
		return "citas/form";
	}
		
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {

		Cita cita = null;
		if (id > 0) {
			cita = citaService.findById(id);
			if (cita == null) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.cita.flash.db.error", null, locale));
				return "redirect:/citas/listar";
			}
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cita.flash.id.error", null, locale));
			return "redirect:/citas/listar";
		}
		
		
		// listas
		model.put("listaEspecialidades", especialidadService.findAll());
		model.put("listaDoctores", doctorService.findByEspecialidad(cita.getEspecialidad().getId()));
		
		model.put("cita", cita);
		model.put("titulo", messageSource.getMessage("text.cita.form.titulo.editar", null, locale));
		return "citas/form";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cita cita, BindingResult result, Model model,
			/*@RequestParam("file") MultipartFile foto,*/ RedirectAttributes flash, SessionStatus status, Locale locale) {

		if (result.hasErrors()) {
			model.addAttribute("listaEspecialidades", especialidadService.findAll());
			model.addAttribute("titulo", messageSource.getMessage("text.cita.form.titulo.crear", null, locale));
			return "citas/form";
		}
		
		// ajustar hora
		String[] horaCita = cita.getHoraCita().split(",");
		cita.setHoraCita(horaCita.length > 1 ? horaCita[1] : horaCita[0]);
		
		/*if (!foto.isEmpty()) {

			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {

				uploadFileService.delete(cliente.getFoto());
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", messageSource.getMessage("text.cliente.flash.foto.subir.success", null, locale) + "'" + uniqueFilename + "'");

			cliente.setFoto(uniqueFilename);

		}*/

		String mensajeFlash = (cita.getId() != null) ? messageSource.getMessage("text.cita.flash.editar.success", null, locale) : messageSource.getMessage("text.cita.flash.crear.success", null, locale);

		citaService.save(cita);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/citas/listar";
	}

	
	private boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
	}
	
}
