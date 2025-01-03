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
public class AutorResponseDTO {
	
	private Long id;
	
	@NotBlank(message = "O nome é obrigatório.")
	private String nome;
	
	public static AutorResponseDTO converterAutorParaAutorResponseDTO(Autor autor) {
		return new AutorResponseDTO(autor.getId(), autor.getNome());
	}
}
