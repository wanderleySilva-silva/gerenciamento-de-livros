package estudos.springboot.thymeleaf.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import estudos.springboot.thymeleaf.entities.Livro;
import estudos.springboot.thymeleaf.exceptions.LivroDuplicadoException;
import estudos.springboot.thymeleaf.exceptions.LivroNotFoundException;
import estudos.springboot.thymeleaf.repositories.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;

	public Livro salvarLivro(Livro livro) throws LivroDuplicadoException {
		
		if(verificarLivroDuplicado(livro)) {
			throw new LivroDuplicadoException("Não é possível salvar este livro, pois ele já está cadastrado.");
		}
		
		return livroRepository.save(livro);
	}
	
	public boolean verificarLivroDuplicado(Livro livro) {
		
		Optional<Livro> livroDuplicado = livroRepository.findByTituloAndAutor(livro.getTitulo(), livro.getAutor());
		
		return livroDuplicado.isPresent();
	}

	public List<Livro> listarLivros() {
		return livroRepository.findAll();
	}

	public void excluirLivro(Long codigo) {
		livroRepository.deleteById(codigo);
	}

	public Livro atualizarLivro(Long codigo, Livro livro) throws LivroNotFoundException {
		Livro livroSalvo = validarSeLivroExiste(codigo);
		livroSalvo.setImagem(livro.getImagem());
		livroSalvo.setTitulo(livro.getTitulo());
		livroSalvo.setAutor(livro.getAutor());
		
		livroRepository.save(livroSalvo);

		return livroSalvo;
	}

	public Livro validarSeLivroExiste(Long codigo) throws LivroNotFoundException {
		Optional<Livro> livro = buscarLivroPorCodigo(codigo);
		if (livro.isPresent()) {
			return livro.get();
		} else {
			throw new LivroNotFoundException("O livro com o código " + codigo + " não foi encontrado.");
		}

	}

	public Optional<Livro> buscarLivroPorCodigo(Long codigo) {
		return livroRepository.findById(codigo);
	}

	public Page<Livro> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		return this.livroRepository.findAll(pageable);
	}

	public List<Livro> buscarLivros(String autor) {

		return livroRepository.findByAutorNomeContainingIgnoreCase(autor);
	}

}
