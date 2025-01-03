package estudos.springboot.thymeleaf.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import estudos.springboot.thymeleaf.dto.autor.AutorRequestDTO;
import estudos.springboot.thymeleaf.dto.autor.AutorResponseDTO;
import estudos.springboot.thymeleaf.entities.Autor;
import estudos.springboot.thymeleaf.exceptions.AutorNotFoundException;
import estudos.springboot.thymeleaf.services.AutorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/autores")
public class AutorController {

	@Autowired
	private AutorService autorService;

	@GetMapping("/novo-autor")
	public String novoAutor(Model model) {
		model.addAttribute("autor", new AutorRequestDTO());

		return "autor/novo-autor";
	}

	@PostMapping("/salvar-autor")
	public String salvar(@ModelAttribute("autor") @Valid AutorRequestDTO autor, BindingResult error,
			RedirectAttributes attributes) {

		if (error.hasErrors()) {
			return "autor/novo-autor";
		}

		autorService.salvar(autor.converterParaEntidade());

		attributes.addFlashAttribute("autorSalvoSucesso", "Autor salvo com sucesso.");

		return "redirect:/autores/listar-autores";
	}

	@GetMapping("/listar-autores")
	public String paginaInicial(Model model) {
		return findPaginated(1, "nome", "asc", model);
	}

	@GetMapping("/excluir-autor/{id}")
	public String excluir(@PathVariable("id") Long id, Model model) {
		autorService.excluir(id);
		return "redirect:/autores/listar-autores";
	}

	@GetMapping("/editar-autor/{id}")
	public String abrirForamularioDeEdicao(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
		AutorRequestDTO autorDto;
		
		try {
			autorDto = AutorRequestDTO.converterAutorParaAutorRequestDTO(autorService.buscarAutorPorId(id));

			model.addAttribute("autor", autorDto);

		} catch (AutorNotFoundException e) {
			attributes.addFlashAttribute("autorNotFound", e.getMessage());

			return "redirect:/autores/listar-autores";
		}

		List<AutorResponseDTO> autores = autorService.listarTodos().stream()
				.map(autor -> AutorResponseDTO.converterAutorParaAutorResponseDTO(autor)).collect(Collectors.toList());

		model.addAttribute("autores", autores);

		return "autor/editar-autor";

	}

	@PostMapping("/editar-autor/{id}")
	public String editar(@PathVariable("id") Long id,
			@ModelAttribute("autor") @Valid AutorRequestDTO autorRequestDTO, BindingResult error, Model model, RedirectAttributes attributes) {

		if (error.hasErrors()) {
			
			return "autor/editar-autor";
		}

		try {

			autorService.editar(id, autorRequestDTO.converterParaEntidade(id));
			
			attributes.addFlashAttribute("autorEditadoSucesso", "Autor editado com sucesso.");
			
		} catch (AutorNotFoundException e) {
			e.printStackTrace();
		}

		return "redirect:/autores/listar-autores";
	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 5;

		Page<Autor> page = autorService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Autor> autores = page.getContent();

		List<AutorResponseDTO> autoresDto = autores.stream()
				.map(autor -> AutorResponseDTO.converterAutorParaAutorResponseDTO(autor)).collect(Collectors.toList());

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("autores", autoresDto);

		return "autor/listar-autores";
	}

	@PostMapping("/buscar")
	public String buscarLivros(Model model, @Param("autor") String autor) {

		List<AutorResponseDTO> autores = autorService.buscarPorNome(autor).stream()
				.map(nome -> AutorResponseDTO.converterAutorParaAutorResponseDTO(nome)).collect(Collectors.toList());

		if (autor == null || autor == "" || autores.isEmpty()) {
			return "redirect:/autores/listar-autores";
		}
		model.addAttribute("autores", autores);

		return "autor/listar-autores";
	}
}
