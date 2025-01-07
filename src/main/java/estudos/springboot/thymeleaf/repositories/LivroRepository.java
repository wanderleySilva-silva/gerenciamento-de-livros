package estudos.springboot.thymeleaf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import estudos.springboot.thymeleaf.entities.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

    List<Livro> findByAutorNomeContainingIgnoreCase(String autor);
    
    boolean existsByTituloAndAutorNome(String titulo, String autorNome);
}