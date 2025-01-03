package estudos.springboot.thymeleaf.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "autor")
public class Autor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy = "autor")
	private List<Livro> livros;
	
	public Autor(Long id) {
		this.id = id;
	}
	
	public Autor(String nome) {
		this.nome = nome;
	}
	
	public Autor(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

}
