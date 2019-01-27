package services;

import java.util.Scanner;

import factories.UserFactory;
import storage.UserStorage;
import users.User;

public class UserService {
	private Scanner sc;

	public UserService() {
		this.sc = new Scanner(System.in);
	}

	public void register(UserStorage userStorage) {
		System.out.println("Please enter email:");
		String email = sc.nextLine();
		System.out.println("Please enter password:");
		String password = sc.nextLine();

		User user = UserFactory.createUser(email, password);

		if (user != null) {
			userStorage.registerUser(user);
		}
	}

	public void login(UserStorage userStorage) {
		System.out.println("Please enter email:");
		String email = sc.nextLine();
		System.out.println("Please enter password:");
		String password = sc.nextLine();
//		User user = userStorage.logIn(email, password);
//		if (user != null) {
//			App.user = user;
//		}
//		System.out.println(App.user.getEmail());

	}
}
