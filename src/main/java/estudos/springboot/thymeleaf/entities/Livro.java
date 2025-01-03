package estudos.springboot.thymeleaf.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;
	private String imagem;
	private String titulo;

	@ManyToOne
	private Autor autor;
	
	public Livro() {
		
	}

	public Livro(String imagem, String titulo, Autor autor) {
		this.imagem = imagem;
		this.titulo = titulo;
		this.autor = autor;
	}
}
