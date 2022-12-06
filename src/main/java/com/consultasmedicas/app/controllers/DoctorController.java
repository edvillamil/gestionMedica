package com.consultasmedicas.app.controllers;

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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.consultasmedicas.app.models.entity.Cita;
import com.consultasmedicas.app.models.entity.Doctor;
import com.consultasmedicas.app.models.entity.Especialidad;
import com.consultasmedicas.app.models.entity.Estado;
import com.consultasmedicas.app.models.entity.Paciente;
import com.consultasmedicas.app.models.service.IDoctorService;
import com.consultasmedicas.app.models.service.IEspecialidadService;

@SessionAttributes("doctor")
@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
		
	@Autowired
	private IDoctorService doctorService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IEspecialidadService especialidadService;
	
	
	@RequestMapping(value = {"/listar"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request,
			Locale locale) {

		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
		}
		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
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
		
		List<Doctor> doctores = doctorService.findAll();

		model.addAttribute("titulo", messageSource.getMessage("text.doctor.listar.titulo", null, locale));
		model.addAttribute("doctores", doctores);

		return "doctor/listar";
	}
	

	@PostMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {

		Doctor doctor = null;
		if (id > 0) {
			doctor = doctorService.findById(id);
			if (doctor == null) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.doctor.flash.db.error", null, locale));
				return "redirect:/doctor/listar";
			}
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.doctor.flash.id.error", null, locale));
			return "redirect:/doctor/listar";
		}
		model.put("doctor", doctor);
		model.put("titulo", messageSource.getMessage("text.doctor.form.titulo.editar", null, locale));
		return "doctor/form";
	}
	
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model, Locale locale) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Doctor doctor = new Doctor();
				
		model.put("listaEspecialidades", especialidadService.findAll());
		
		model.put("doctor", doctor);
		model.put("titulo", messageSource.getMessage("text.cita.form.titulo.crear", null, locale));
		return "doctor/form";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Doctor doctor, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status, Locale locale) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.doctor.form.titulo", null, locale));
			return "especialidad/form";
		}

		String mensajeFlash = (doctor.getId() != null) ? messageSource.getMessage("text.doctor.flash.editar.success", null, locale) : messageSource.getMessage("text.especialidad.flash.crear.success", null, locale);

		doctorService.save(doctor);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {

		if (id > 0) {
			Doctor doctor = doctorService.findById(id);
	
			if(doctor.getId() != null) {
				doctorService.delete(id);
				flash.addFlashAttribute("info", messageSource.getMessage("text.doctor.flash.eliminar.success", null, locale));		
				//flash.addFlashAttribute("success", messageSource.getMessage("text.doctor.flash.db.foreign", null, locale));
			} else {
				doctorService.delete(id);
				flash.addFlashAttribute("info", messageSource.getMessage("text.doctor.flash.eliminar.success", null, locale));		
			}
		}
		return "redirect:/doctor/listar";
	}

	@GetMapping(value = "/cargar-doctores/{term}", produces = { "application/json" })
	public @ResponseBody List<Doctor> cargarDoctores(@PathVariable String term) {
		List<Doctor> doctores = doctorService.findByEspecialidad(Long.parseLong(term));
		return doctores;
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
