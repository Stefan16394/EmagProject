package exceptions;

public class UserRegisterException extends Exception {

	private static final long serialVersionUID = 1433642012347445386L;

	public UserRegisterException() {
		super();
	}

	public UserRegisterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserRegisterException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserRegisterException(String message) {
		super(message);
	}

	public UserRegisterException(Throwable cause) {
		super(cause);
	}

}
