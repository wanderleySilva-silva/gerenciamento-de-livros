package estudos.springboot.thymeleaf.controllers;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@GetMapping("/home")
	public String indexAdministrador(Model model) {
		
		model.addAttribute("anoAtual", LocalDate.now().getYear());
		
		return "administrador/home-admin";
	}

}
