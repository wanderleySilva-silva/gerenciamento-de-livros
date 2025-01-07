package estudos.springboot.thymeleaf.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import estudos.springboot.thymeleaf.entities.Autor;
import estudos.springboot.thymeleaf.entities.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

    List<Livro> findByAutorNomeContainingIgnoreCase(String autor);
    
    Optional<Livro> findByTituloAndAutor(String titulo, Autor autor);
}