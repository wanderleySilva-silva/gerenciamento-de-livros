package estudos.springboot.thymeleaf.exceptions;

public class AutorNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public AutorNotFoundException(String message) {
		super(message);
	}

}
