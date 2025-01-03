package estudos.springboot.thymeleaf.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String nome;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios = new ArrayList<Usuario>();

    @Override
    public String getAuthority() {
        return this.nome;
    }

}
