package com.consultasmedicas.app.controllers;

import java.security.Principal;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.consultasmedicas.app.models.entity.Cita;
import com.consultasmedicas.app.models.entity.Paciente;
import com.consultasmedicas.app.models.service.IPacienteService;

@Controller
public class LoginController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@GetMapping(value = {"/login","/"})
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required = false) String logout,
			Model model, Principal principal, RedirectAttributes flash, Locale locale) {
		
		if(principal != null) {
			flash.addFlashAttribute("info", messageSource.getMessage("text.login.already", null, locale));
			return "redirect:/main";
		}
		
		if(error != null) {
			model.addAttribute("error", messageSource.getMessage("text.login.error", null, locale));
		}
		
		if(logout != null) {
			model.addAttribute("success", messageSource.getMessage("text.login.logout", null, locale));
		}
		
		return "login";
	}
	
	@GetMapping(value = {"/main"})
	public String principal(
			Model model, 
			Authentication authentication,
			HttpServletRequest request,
			Locale locale) {
		
		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Paciente paciente = new Paciente();

		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
		}
		
		if(hasRole("ROLE_PACIENTE")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
			paciente = pacienteService.findPacienteByUsername(auth.getName());
			
			
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		
		return "main";
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
