package estudos.springboot.thymeleaf.dto.livro;

import estudos.springboot.thymeleaf.dto.autor.AutorResponseDTO;
import estudos.springboot.thymeleaf.entities.Livro;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LivroResponseDTO {
	
	private Long codigo;

	private String imagem;

	@NotBlank(message = "O título é obrigatório.")
	private String titulo;

	private AutorResponseDTO autor;

	public static LivroResponseDTO converterLivroParaLivroResponseDTO(Livro livro) {
		return new LivroResponseDTO(livro.getCodigo(), livro.getImagem(), livro.getTitulo(),
				AutorResponseDTO.converterAutorParaAutorResponseDTO(livro.getAutor()));
	}
}
