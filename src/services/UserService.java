package services;

import java.util.Scanner;

import factories.UserFactory;
import storage.UserStorage;
import users.User;

public class UserService {
	private static Scanner sc = new Scanner(System.in);

	public void register(UserStorage userStorage) {
		System.out.println("Please enter email:");
		String email = sc.nextLine();
		System.out.println("Please enter password:");
		String password = sc.nextLine();

		User user = UserFactory.createUser(email, password, false);

		if (user != null) {
			userStorage.registerUser(user);
		}
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
	
	public void checkShoppingCart(User user) {
		while(true) {
			System.out.println("Enter command: 1 - Show all products 2 - Buy all products 3 - Remove product "
					+ "4 - Change the quantity of some product 5 - Exit");
			String command = sc.nextLine();
			switch (command) {
			case "1":
				user.getCart().showProductsInTheCart();
				break;
			case "2":
				user.getCart().buyAllProducts();
				break;
			case "3":
				user.getCart().removeProduct();
				break;
			default:
				return;
			}
		}
//		System.out.println("");
	}
}
