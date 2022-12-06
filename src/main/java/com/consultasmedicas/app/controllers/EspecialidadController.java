package com.consultasmedicas.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.consultasmedicas.app.models.entity.Especialidad;
import com.consultasmedicas.app.models.service.IEspecialidadService;

@SessionAttributes("especialidad")
@Controller
@RequestMapping("/especialidad")
public class EspecialidadController {
	
	@Autowired
	IEspecialidadService especialidadService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {

		Especialidad especialidad = especialidadService.findById(id);
		if (especialidad == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.especialidad.flash.db.error", null, locale));
			return "redirect:/especialidad/listar";
		}

		model.put("especialidad", especialidad);
		model.put("titulo", messageSource.getMessage("text.especialidad.detalle.titulo", null, locale).concat(": ").concat(especialidad.getNombre()));
		return "/especialidad/ver";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model, Locale locale) {

		Especialidad especialidad = new Especialidad();
		model.put("especialidad", especialidad);
		model.put("titulo", messageSource.getMessage("text.especialidad.form.titulo.crear", null, locale));
		return "especialidad/form";
	}
	
	@RequestMapping(value = {"/listar"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request,
			Locale locale) {

		
		List<Especialidad> especialidades = especialidadService.findAll();

		model.addAttribute("titulo", messageSource.getMessage("text.especialidad.listar.titulo", null, locale));
		model.addAttribute("especialidades", especialidades);

		return "especialidad/listar";
	}
	
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {

		Especialidad especialidad = null;
		if (id > 0) {
			especialidad = especialidadService.findById(id);
			if (especialidad == null) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
				return "redirect:/especialidad/listar";
			}
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.id.error", null, locale));
			return "redirect:/especialidad/listar";
		}
		model.put("especialidad", especialidad);
		model.put("titulo", messageSource.getMessage("text.especialidad.editar", null, locale));
		return "especialidad/form";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Especialidad especialidad, BindingResult result, Model model,
			/*@RequestParam("file") MultipartFile foto,*/ RedirectAttributes flash, SessionStatus status, Locale locale) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.especialidad.form.titulo", null, locale));
			return "especialidad/form";
		}

		/*if (foto != null && !foto.isEmpty()) {

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

		String mensajeFlash = (especialidad.getId() != null) ? messageSource.getMessage("text.especialidad.flash.editar.success", null, locale) : messageSource.getMessage("text.especialidad.flash.crear.success", null, locale);

		especialidadService.save(especialidad);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {

		if (id > 0) {
			Especialidad especialidad = especialidadService.findById(id);
	
			if(especialidad.getDoctores() != null && especialidad.getDoctores().size() > 0) {
				flash.addFlashAttribute("success", messageSource.getMessage("text.especialidad.flash.db.foreign", null, locale));
			} else {
				especialidadService.delete(id);
				flash.addFlashAttribute("info", messageSource.getMessage("text.especialidad.flash.eliminar.success", null, locale));		
			}
			
			/*if (uploadFileService.delete(cliente.getFoto())) {
				String mensajeFotoEliminar = String.format(messageSource.getMessage("text.especialidad.flash.foto.eliminar.success", null, locale), cliente.getFoto());
				flash.addFlashAttribute("info", mensajeFotoEliminar);
			}*/

		}
		return "redirect:/especialidad/listar";
	}
	
	@GetMapping(value = "/cargar-especialidades/{term}", produces = { "application/json" })
	public @ResponseBody List<Especialidad> cargarEspcialidades(@PathVariable String term) {
		ArrayList<Especialidad> esp = (ArrayList<Especialidad>)especialidadService.findByNombre(term);
		return esp;
	}

}
