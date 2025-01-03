package estudos.springboot.thymeleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import estudos.springboot.thymeleaf.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByUserName(String userName);

	boolean existsByUserName(String userName);


}
