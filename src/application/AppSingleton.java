package application;

import java.util.Scanner;

import factories.UserFactory;
import services.ProductService;
import services.UserService;
import storage.ProductStorage;
import storage.UserStorage;
import suppliers.Distributor;
import users.User;

public class AppSingleton {
	private static App application;
	
	public static App getInstance() {
		if(application==null) {
			application = new App();
		}
		return application;
	}
	
	public static class App {
		private UserService userService;
		private UserStorage userStorage;
		private ProductService productService;
		private ProductStorage productStorage;
		private User currentUser;

		private App() {
			this.productStorage = new ProductStorage();
			this.productStorage.setDistributor(new Distributor(this.productStorage));
			this.productService = new ProductService();
			this.productService.setProductStorage(this.productStorage);
			this.userService = new UserService();
			this.userStorage = new UserStorage();
		}

		public void startApp() {
			Scanner sc = new Scanner(System.in);

			this.productService.generateRandomProducts();
			this.productStorage.getDistributor().start();
//			System.out.println();
//			System.out.println();
//			System.out.println();
//			System.out.println();
//			this.productStorage.listAllProducts();
			this.userStorage.registerUser(UserFactory.createUser("admin@emag.bg", "admin", true));
			this.userStorage.registerUser(UserFactory.createUser("a@a.bg", "1234", false));
			this.currentUser = this.userStorage.logIn("a@a.bg","1234");
			

			while (true) {
				if (this.currentUser != null && this.currentUser.isAdmin()) {
					System.out.println();
					System.out.println("1 - Add product, 2 - Delete product, 3 - Log out");
					String command = sc.nextLine();
					switch (command) {
					case "1":
						this.productService.createProduct();
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
				} else {
					System.out.println("Enter command: 1 - register User, 2 - log in,"
							+ " 3 - Find Product By Category, 4 - Delete account, 5 - Log out,"
							+ " 6 - Check out the things in your cart");
					String command = sc.nextLine();
					switch (command) {
					case "1":
						this.userService.register(this.userStorage);
						break;
					case "2":
						User user = this.userService.login(this.userStorage);
						if (user != null) {
							this.currentUser = user;
							System.out.println("You logged in successfully!");
						} else {
							System.out.println("Wrong email or password!");
						}
						break;
					case "3":
						this.productService.findProductsByCategory(this.currentUser);
						break;
					case "4":
						if (this.currentUser != null) {
							this.userService.deleteAccount(this.userStorage, this.currentUser);
						} else {
							System.out.println("First you need to log in!");
						}
						break;
					case "5":
						if (this.currentUser != null) {
							this.currentUser = null;
							System.out.println("You logged out successfully!");
						} else {
							System.out.println("First you need to log in!");
						}
						break;
					case "6":
						if (this.currentUser != null) {
							this.productService.checkShoppingCart(this.currentUser);
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
}
