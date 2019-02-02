package application;

import java.util.Scanner;

import exceptions.UserRegisterException;
import factories.UserFactory;
import services.ProductService;
import services.UserService;
import storage.ProductStorage;
import storage.UserStorage;
import users.User;

public class App {
	private UserService userService;
	private UserStorage userStorage;
	private ProductService productService;
	private ProductStorage productStorage;
	private User currentUser;

	public App() {
		this.userService = new UserService();
		this.userStorage = new UserStorage();
		this.productService = new ProductService();
		this.productStorage = new ProductStorage();
	}

	public void startApp() {
		Scanner sc = new Scanner(System.in);

		this.productService.generateRandomProducts(this.productStorage);
//		System.out.println();
//		System.out.println();
//		System.out.println();
//		System.out.println();
//		this.productStorage.listAllProducts();
		this.userStorage.registerUser(UserFactory.createUser("admin@emag.bg", "admin", true));

		while (true) {
			if (this.currentUser != null) {
				if (this.currentUser.isAdmin()) {
					System.out.println("1 - Add product, 2 - Delete product, 3 - Log out");
					String command = sc.nextLine();
					switch (command) {
					case "1":
						this.productService.createProduct(this.productStorage);
						break;
					case "3":
						if (this.currentUser != null) {
							this.currentUser = null;
							System.out.println("You logged out successfully!");
						} else {
							System.out.println("First you need to log in!");
						}
						break;
					default:
						System.out.println("Invalid command!");
						break;
					}
				}
			} else {
				System.out.println("Enter command: 1 - register User, 2 - log in, 3 - list users, "
						+ "4 - Find Product By Category, 5 - Delete account, 6 - Log out, 7 - Add to your cart,"
						+ " 8 - Check out the things in your cart");
				String command = sc.nextLine();
				switch (command) {
				case "1":
					this.userService.register(this.userStorage);
					break;
				case "2":
					User user = this.userService.login(this.userStorage);
					if (user != null) {
						this.currentUser = user;
					}
					System.out.println(this.currentUser.getEmail());
					break;
				case "3":
					userStorage.listUsers();
					break;
				case "4":
					this.productService.findProductsByCategory(this.productStorage);
					break;
				case "5":
					if (this.currentUser != null) {
						this.userService.deleteAccount(this.userStorage, this.currentUser);
					} else {
						System.out.println("First you need to log in!");
					}
					break;
				case "6":
					if (this.currentUser != null) {
						this.currentUser = null;
						System.out.println("You logged out successfully!");
					} else {
						System.out.println("First you need to log in!");
					}
					break;
				default:
					System.out.println("Invalid command!");
					break;
				}
				System.out.println();
			}
		}
	}
}
