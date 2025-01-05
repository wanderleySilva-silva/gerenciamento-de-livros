package estudos.springboot.thymeleaf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import estudos.springboot.thymeleaf.entities.Usuario;
import estudos.springboot.thymeleaf.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	 @Autowired
	    private UsuarioRepository usuarioRepository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	        Usuario usuarioEncontrado = usuarioRepository.findByUsername(username);

	        if (usuarioEncontrado == null) {
	            throw new UsernameNotFoundException("Usuário não foi encontrado.");
	        }
	        return new User(usuarioEncontrado.getUsername(),
	                usuarioEncontrado.getPassword(),
	                usuarioEncontrado.isEnabled(), true, true, true, usuarioEncontrado.getAuthorities());
	    }

}
