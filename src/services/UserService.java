package services;

import java.util.Scanner;

import factories.UserFactory;
import storage.ProductStorage;
import storage.UserStorage;
import users.User;

public class UserService {
	private static Scanner sc = new Scanner(System.in);

	public User register(UserStorage userStorage) {
		System.out.println("Please enter email:");
		String email = sc.nextLine();
		System.out.println("Please enter password:");
		String password = sc.nextLine();

		User user = UserFactory.createUser(email, password, false);

		if (user != null) {
			userStorage.registerUser(user);
		}
		return user;
	}

	public User login(UserStorage userStorage) {
		System.out.println("Please enter email:");
		String email = sc.nextLine();
		System.out.println("Please enter password:");
		String password = sc.nextLine();
		User user = userStorage.logIn(email, password);
		return user;
	}

	public void deleteAccount(UserStorage userStorage, User currentUser) {
		userStorage.deleteAccount(currentUser);
	}
}
