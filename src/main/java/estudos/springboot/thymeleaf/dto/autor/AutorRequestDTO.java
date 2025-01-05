package estudos.springboot.thymeleaf.dto.autor;

import estudos.springboot.thymeleaf.entities.Autor;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AutorRequestDTO {
	
	private Long id;
	
	@NotBlank(message = "O nome é obrigatório.")
	private String nome;
	
	public static AutorRequestDTO converterAutorParaAutorRequestDTO(Autor autor) {
		return new AutorRequestDTO(autor.getId(), autor.getNome());
		
	}
	
	public Autor converterParaEntidade() {
		return new Autor(nome);
	}
	
	public Autor converterParaEntidade(Long id) {
		return new Autor(id, nome);
	}
}
