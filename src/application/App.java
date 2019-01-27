package application;

import java.util.Scanner;
import services.UserService;
import storage.UserStorage;

public class App {
	private UserService userService;
	private UserStorage userStorage;
	
	public App() {
		this.userService = new UserService();
		this.userStorage = new UserStorage();
	}
	
	public void startApp() {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("Enter command: 1- register User, 2- logIn, 3-listusers");
			int command = sc.nextInt();
			sc.nextLine();
			switch (command) {
			case 1:
				this.userService.register(this.userStorage);
				break;
			case 2:
				this.userService.login(this.userStorage);
				break;
			case 3:
				userStorage.listUsers();
				break;
			default:
				return;
			}
			System.out.println();
		}	 
	}
}
