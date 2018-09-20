package Exceptions;

public class InvalidDataException extends Exception {

	private static final long serialVersionUID = 7441462494273193832L;

	public InvalidDataException() {
	}

	public InvalidDataException(String message) {
		super(message);
	}
}