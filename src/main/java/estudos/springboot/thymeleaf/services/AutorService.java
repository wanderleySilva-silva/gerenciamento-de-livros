package estudos.springboot.thymeleaf.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import estudos.springboot.thymeleaf.entities.Autor;
import estudos.springboot.thymeleaf.exceptions.AutorNotFoundException;
import estudos.springboot.thymeleaf.repositories.AutorRepository;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository autorRepository;
	
	public Autor salvar(Autor autor) {
		return autorRepository.save(autor);
	}
	
	public List<Autor> listarTodos(){
		return autorRepository.findAll();
	}
	
	public Autor buscarAutorPorId(Long id) throws AutorNotFoundException {
		Optional<Autor> autorOptional = autorRepository.findById(id);
		
		if(autorOptional.isPresent()) {
			return autorOptional.get();
		} else {
			throw new AutorNotFoundException("O autor com o ID " + id + " não foi encontrado.");
		}
	}
	
	public Autor editar(Long id, Autor autor) throws AutorNotFoundException {
		Autor autorEncontrado = buscarAutorPorId(id);
		
		autorEncontrado.setNome(autor.getNome());
		
		return autorRepository.save(autorEncontrado);
	}
	
	public void excluir(Long id) {
		autorRepository.deleteById(id);
	}
	
	public List<Autor> buscarPorNome(String nome) {
	    if (nome == null || nome.trim().isEmpty()) {
	        return Collections.emptyList(); // Retorna uma lista vazia se o nome for nulo ou vazio.
	    }
	    return autorRepository.findByNomeContainingIgnoreCase(nome);
	}
	
	public Page<Autor> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		return this.autorRepository.findAll(pageable);
	}
}
