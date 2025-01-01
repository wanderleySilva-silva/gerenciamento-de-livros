package estudos.springboot.thymeleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import estudos.springboot.thymeleaf.entities.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{

}
