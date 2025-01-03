package estudos.springboot.thymeleaf.dto.livro;

import estudos.springboot.thymeleaf.entities.Autor;
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
public class LivroRequestDTO {
	
	private Long codigo;

	private String imagem;

	@NotBlank(message = "O título é obrigatório.")
	private String titulo;

	private Long autorId;
	
    public static LivroRequestDTO converterLivroPraLivroRequestDTO(Livro livro) {
        return new LivroRequestDTO(
        	livro.getCodigo(),
            livro.getImagem(),
            livro.getTitulo(),
            livro.getAutor().getId()
        );
    }

	public Livro converterParaEntidade() {
		return new Livro(imagem, titulo, new Autor(autorId));
	}

	public Livro converterParaEntidade(Long codigo) {
		return new Livro(codigo, imagem, titulo, new Autor(autorId));
	}

}
