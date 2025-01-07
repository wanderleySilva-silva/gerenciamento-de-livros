package estudos.springboot.thymeleaf.exceptions;

public class LivroDuplicadoException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public LivroDuplicadoException(String message) {
		super(message);
	}

}
