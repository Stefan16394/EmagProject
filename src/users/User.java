package users;

import exceptions.UserRegisterException;
import regexPatterns.RegexPatterns;

public class User {
	private String email;
	private String password;

	public User(String email, String password) throws UserRegisterException {
		try {
			this.setEmail(email);
			this.setPassword(password);
		} catch (UserRegisterException e) {
			throw new UserRegisterException("Registration unsuccessfull.", e);
		}
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) throws UserRegisterException {
		boolean isValid = RegexPatterns.validateEmail(email);
		if (!isValid) {
			throw new UserRegisterException("Invalid email pattern. Please try again.");
		}
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws UserRegisterException {
		boolean isValid = RegexPatterns.validatePassword(password);
		if (!isValid) {
			throw new UserRegisterException("Invalid password pattern. Please try again.");
		}
		this.password = password;
	}
}
