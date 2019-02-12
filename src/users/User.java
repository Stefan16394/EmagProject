package users;

import java.util.ArrayDeque;
import java.util.Deque;

import exceptions.UserRegisterException;
import utilities.RegexPatterns;

public class User implements IObserver {
	private String email;
	private String password;
	private boolean isAdmin;
	private Deque<Message> messages;
	private ShoppingCart cart;

	public User(String email, String password, boolean isAdmin) throws UserRegisterException {
		try {
			this.setEmail(email);
			this.setPassword(password);
			this.isAdmin = isAdmin;
			this.cart = new ShoppingCart();
			this.messages = new ArrayDeque<Message>();
		} catch (UserRegisterException e) {
			throw new UserRegisterException("Registration unsuccessfull.", e);
		}
	}

	public boolean isAdmin() {
		return isAdmin;
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

	public ShoppingCart getCart() {
		return cart;
	}
	

	@Override
	public void react(Message message) {
		if(message != null) {
			this.messages.push(message);
		}
	}
	
	public void checkMessages() {
		this.messages.forEach(m -> System.out.println(m));
	}
	
}
