package application;

import java.util.Scanner;

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
		
		while(true) {
			System.out.println("Enter command: 1- register User, 2- logIn, 3-listusers, 4-Add Product");
			String command = sc.nextLine();
			switch (command) {
			case "1":
				this.userService.register(this.userStorage);
				break;
			case "2":
				User user = this.userService.login(this.userStorage);
				if(user!=null) {
					this.currentUser = user;
				}
				System.out.println(this.currentUser.getEmail());
				break;
			case "3":
				userStorage.listUsers();
				break;
			case "4":
				this.productService.createProduct(this.productStorage);
				break;
			default:
				System.out.println("Invalid command!");
			}
			System.out.println();
		}	 
	}
}
