package estudos.springboot.thymeleaf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PaginaErroController {
	
	@RequestMapping("/erro")
	public String handleError() {

		return "erro/erro";
	}
}
