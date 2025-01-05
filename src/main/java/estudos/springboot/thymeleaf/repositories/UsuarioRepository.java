package estudos.springboot.thymeleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import estudos.springboot.thymeleaf.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByUsername(String username);

	boolean existsByUsername(String username);


}
