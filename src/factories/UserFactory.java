package factories;

import exceptions.UserRegisterException;
import users.User;

public class UserFactory {
	
	public static User createUser(String email, String password) {
		User user = null;
		try {
			user = new User(email, password);
		}catch(UserRegisterException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause().getMessage());
		}
		return user;
	}
}
