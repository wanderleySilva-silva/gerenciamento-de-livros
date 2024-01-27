package estudos.springboot.thymeleaf.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import estudos.springboot.thymeleaf.modelo.Livro;

public interface LivroRepositorio extends JpaRepository<Livro, Long>{

    List<Livro> findByAutorContainingIgnoreCase(String autor);
}