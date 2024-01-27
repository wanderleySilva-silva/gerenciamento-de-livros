package estudos.springboot.thymeleaf.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import estudos.springboot.thymeleaf.modelo.Livro;
import estudos.springboot.thymeleaf.repositorio.LivroRepositorio;

@Service
public class LivroServico {

	@Autowired
	private LivroRepositorio livroRepositorio;

	public Livro salvarLivro(Livro livro) {
		return livroRepositorio.save(livro);
	}

	public List<Livro> listarLivros() {
		return livroRepositorio.findAll();
	}

	public void excluirLivro(Long codigo) {
		livroRepositorio.deleteById(codigo);
	}

	public Livro atualizarLivro(Long codigo, Livro livro) {
		Livro livroSalvo = validarSeLivroExiste(codigo);
		BeanUtils.copyProperties(livro, livroSalvo, "codigo");
		Livro livroAtualizado = livroSalvo;
		livroRepositorio.save(livroAtualizado);

		return livroAtualizado;
	}

	public Livro validarSeLivroExiste(Long codigo) {
		Optional<Livro> livro = buscarLivroPorCodigo(codigo);
		if (livro.isPresent()) {
			return livro.get();
		} else {
			throw new EmptyResultDataAccessException(1);
		}

	}

	public Optional<Livro> buscarLivroPorCodigo(Long codigo) {
		return livroRepositorio.findById(codigo);
	}

	public Page<Livro> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		return this.livroRepositorio.findAll(pageable);
	}

	public List<Livro> buscarLivros(String autor) {

		return livroRepositorio.findByAutorContainingIgnoreCase(autor);
	}

}
