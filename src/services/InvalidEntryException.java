package services;

public class InvalidEntryException extends Exception {

	private static final long serialVersionUID = 409686925817469751L;
	
	public InvalidEntryException() {
		super();
	}

	public InvalidEntryException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public InvalidEntryException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidEntryException(String arg0) {
		super(arg0);
	}

	public InvalidEntryException(Throwable arg0) {
		super(arg0);
	}
}