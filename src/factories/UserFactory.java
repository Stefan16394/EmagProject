package factories;

import exceptions.UserRegisterException;
import users.User;

public class UserFactory {

	public static User createUser(String email, String password, boolean isAdmin) {
		User user = null;
		try {
			user = new User(email, password, isAdmin);
		} catch (UserRegisterException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause().getMessage());
		}
		return user;
	}
}
