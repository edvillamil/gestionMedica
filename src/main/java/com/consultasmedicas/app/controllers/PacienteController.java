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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.consultasmedicas.app.models.entity.Doctor;
import com.consultasmedicas.app.models.entity.Paciente;
import com.consultasmedicas.app.models.service.IPacienteService;

@SessionAttributes("paciente")
@Controller
@RequestMapping("/paciente")
public class PacienteController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private MessageSource messageSource;
	
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
		
		List<Paciente> pacientes = pacienteService.findAll();

		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("pacientes", pacientes);

		return "paciente/listar";
	}
	
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {

		Paciente paciente = null;
		if (id > 0) {
			paciente = pacienteService.findById(id);
			if (paciente == null) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.paciente.flash.db.error", null, locale));
				return "redirect:/paciente/listar";
			}
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.paciente.flash.id.error", null, locale));
			return "redirect:/paciente/listar";
		}
		model.put("paciente", paciente);
		model.put("titulo", messageSource.getMessage("text.paciente.form.titulo.editar", null, locale));
		return "paciente/form";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Paciente paciente, BindingResult result, Model model,
			/*@RequestParam("file") MultipartFile foto,*/ RedirectAttributes flash, SessionStatus status, Locale locale) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.paciente.form.titulo", null, locale));
			return "paciente/form";
		}

		String mensajeFlash = (paciente.getId() != null) ? messageSource.getMessage("text.paciente.flash.editar.success", null, locale) : messageSource.getMessage("text.paciente.flash.crear.success", null, locale);

		pacienteService.save(paciente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/paciente/listar";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {

		if (id > 0) {
			Paciente paciente = pacienteService.findById(id);
	
			if(paciente.getId() != null) {
				pacienteService.delete(id);
				flash.addFlashAttribute("info", messageSource.getMessage("text.doctor.flash.eliminar.success", null, locale));		
				//flash.addFlashAttribute("success", messageSource.getMessage("text.doctor.flash.db.foreign", null, locale));
			} else {
				pacienteService.delete(id);
				flash.addFlashAttribute("info", messageSource.getMessage("text.doctor.flash.eliminar.success", null, locale));		
			}
			
			/*if (uploadFileService.delete(cliente.getFoto())) {
				String mensajeFotoEliminar = String.format(messageSource.getMessage("text.especialidad.flash.foto.eliminar.success", null, locale), cliente.getFoto());
				flash.addFlashAttribute("info", mensajeFotoEliminar);
			}*/

		}
		return "redirect:/paciente/listar";
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
