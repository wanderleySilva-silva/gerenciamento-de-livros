package estudos.springboot.thymeleaf.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import estudos.springboot.thymeleaf.entities.Livro;
import estudos.springboot.thymeleaf.services.LivroService;
import estudos.springboot.thymeleaf.services.PdfService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LivroController {

	@Autowired
	private LivroService livroService;
	
	@Autowired
	PdfService pdfService;
	
	@GetMapping("/novo-livro")
	public String cadastrarLivro(Model model) {
		Livro livro = new Livro();
		model.addAttribute("livro", livro);
		return "novo-livro";
	}
	
	@PostMapping("/salvar-livro")
	public String salvarLivro(@ModelAttribute("livro") Livro livro) {
		livroService.salvarLivro(livro);
		return "redirect:/novo-livro";
	}
	
	@GetMapping("/")
    public String paginaInicial(Model model) {
        return findPaginated(1, "titulo", "asc", model);
    }
	
	@GetMapping("/excluir-livro/{codigo}")
	public String excluirLivro(@PathVariable("codigo") Long codigo, Model model) {
		livroService.excluirLivro(codigo);
		return "redirect:/";
	}
	@GetMapping("/editar-livro/{codigo}")
	public String abrirForamularioDeEdicao(@PathVariable("codigo") Long codigo, Model model) {
		Livro livro = livroService.validarSeLivroExiste(codigo);
		model.addAttribute("livro", livro);
		
		return "/atualizar-livro";
		
	}
	@PostMapping("/editar-livro/{codigo}")
	public String atualizarLivro(@PathVariable("codigo") Long codigo, @ModelAttribute("livro") Livro livro) {
		livroService.atualizarLivro(codigo, livro);
		
		return "redirect:/";
	}
	@GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 6;
 
        Page<Livro> page = livroService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Livro> livros = page.getContent();
 
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
 
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
 
        model.addAttribute("livros", livros);
        return "/lista-de-livros";
    }
	
	@PostMapping("/buscar")
	public String buscarLivros(Model model, @Param("autor") String autor) {
		
		List<Livro> livros = livroService.buscarLivros(autor);
		
		if (autor == null || autor == "" || livros.isEmpty()) {
			return "redirect:/";
		}
		model.addAttribute("livros", livros);
		
		return "/lista-de-livros";
	}
	
}
