package regexPatterns;

public class RegexPatterns {
	private static final String EMAIL_PATTERN = "^[A-Za-z0-9]+@[A-Za-z]+(\\.[A-Za-z]+)+$";
	private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{3,14}$";

	public static boolean validateEmail(String email) {
		return email.matches(EMAIL_PATTERN);
	}

	public static boolean validatePassword(String password) {
		return password.matches(PASSWORD_PATTERN);
	}
}
