package estudos.springboot.thymeleaf.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estudos.springboot.thymeleaf.entities.Autor;
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
}
