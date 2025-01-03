package estudos.springboot.thymeleaf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import estudos.springboot.thymeleaf.entities.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{
	
	List<Autor> findByNomeContainingIgnoreCase(String nome);

}
