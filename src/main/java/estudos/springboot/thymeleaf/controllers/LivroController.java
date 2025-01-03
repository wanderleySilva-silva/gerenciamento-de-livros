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

import estudos.springboot.thymeleaf.dto.autor.AutorResponseDTO;
import estudos.springboot.thymeleaf.dto.livro.LivroRequestDTO;
import estudos.springboot.thymeleaf.dto.livro.LivroResponseDTO;
import estudos.springboot.thymeleaf.entities.Livro;
import estudos.springboot.thymeleaf.exceptions.LivroNotFoundException;
import estudos.springboot.thymeleaf.services.AutorService;
import estudos.springboot.thymeleaf.services.LivroService;
import estudos.springboot.thymeleaf.services.PdfService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/livros")
public class LivroController {

	@Autowired
	private LivroService livroService;

	@Autowired
	private AutorService autorService;

	@Autowired
	PdfService pdfService;

	@GetMapping("/novo-livro")
	public String cadastrarLivro(Model model) {

		LivroRequestDTO livro = new LivroRequestDTO();

		List<AutorResponseDTO> autores = autorService.listarTodos().stream()
				.map(autor -> AutorResponseDTO.converterAutorParaAutorResponseDTO(autor)).collect(Collectors.toList());

		model.addAttribute("livro", livro);

		model.addAttribute("autores", autores);

		return "livro/novo-livro";
	}

	@PostMapping("/salvar-livro")
	public String salvarLivro(@ModelAttribute("livro") @Valid LivroRequestDTO livroRequestDTO, BindingResult error,
			Model model, RedirectAttributes attributes) {

		if (error.hasErrors()) {
			List<AutorResponseDTO> autores = autorService.listarTodos().stream()
					.map(autor -> AutorResponseDTO.converterAutorParaAutorResponseDTO(autor))
					.collect(Collectors.toList());

			model.addAttribute("autores", autores);

			return "livro/novo-livro";

		}

		Livro novoLivro = livroRequestDTO.converterParaEntidade();

		livroService.salvarLivro(novoLivro);
		
		attributes.addFlashAttribute("livroSalvoSucesso", "Livro salvo com sucesso.");

		return "redirect:/livros/novo-livro";
	}

	@GetMapping("/listar-livros")
	public String paginaInicial(Model model) {
		return findPaginated(1, "titulo", "asc", model);
	}

	@GetMapping("/excluir-livro/{codigo}")
	public String excluirLivro(@PathVariable("codigo") Long codigo, Model model) {
		livroService.excluirLivro(codigo);
		return "redirect:/livros/listar-livros";
	}

	@GetMapping("/editar-livro/{codigo}")
	public String abrirForamularioDeEdicao(@PathVariable("codigo") Long codigo, Model model, RedirectAttributes attributes) {
		LivroRequestDTO livro;
		try {
			livro = LivroRequestDTO.converterLivroPraLivroRequestDTO(livroService.validarSeLivroExiste(codigo));
			
			model.addAttribute("livro", livro);
			
		} catch (LivroNotFoundException e) {
			attributes.addFlashAttribute("livroNotFound", e.getMessage());
			
			return "redirect:/livros/listar-livros";
		}

		List<AutorResponseDTO> autores = autorService.listarTodos().stream()
				.map(autor -> AutorResponseDTO.converterAutorParaAutorResponseDTO(autor)).collect(Collectors.toList());

		model.addAttribute("autores", autores);

		return "livro/atualizar-livro";

	}

	@PostMapping("/editar-livro/{codigo}")
	public String atualizarLivro(@PathVariable("codigo") Long codigo,
			@ModelAttribute("livro") @Valid LivroRequestDTO livroRequestDTO, BindingResult error, Model model) {
		
		if(error.hasErrors()) {
			List<AutorResponseDTO> autores = autorService.listarTodos().stream()
					.map(autor -> AutorResponseDTO.converterAutorParaAutorResponseDTO(autor))
					.collect(Collectors.toList());

			model.addAttribute("autores", autores);

			return "livro/atualizar-livro";
		}
		
		try {
			
			livroService.atualizarLivro(codigo, livroRequestDTO.converterParaEntidade(codigo));
		} catch (LivroNotFoundException e) {
			e.printStackTrace();
		}

		return "redirect:/livros/listar-livros";
	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 5;

		Page<Livro> page = livroService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Livro> livros = page.getContent();

		List<LivroResponseDTO> livrosDto = livros.stream()
				.map(livro -> LivroResponseDTO.converterLivroParaLivroResponseDTO(livro)).collect(Collectors.toList());

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("livros", livrosDto);

		return "livro/lista-de-livros";
	}

	@PostMapping("/buscar")
	public String buscarLivros(Model model, @Param("autor") String autor) {

		List<LivroResponseDTO> livros = livroService.buscarLivros(autor).stream()
				.map(livro -> LivroResponseDTO.converterLivroParaLivroResponseDTO(livro)).collect(Collectors.toList());

		if (autor == null || autor == "" || livros.isEmpty()) {
			return "redirect:/livros/listar-livros";
		}
		model.addAttribute("livros", livros);

		return "livro/lista-de-livros";
	}

}
