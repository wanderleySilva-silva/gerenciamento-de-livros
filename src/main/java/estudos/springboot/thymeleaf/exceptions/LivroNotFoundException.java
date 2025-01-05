package estudos.springboot.thymeleaf.exceptions;

public class LivroNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public LivroNotFoundException(String message) {
		super(message);
	}

}
