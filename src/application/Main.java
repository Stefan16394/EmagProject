package application;

import application.AppSingleton.App;

public class Main {
	public static void main(String[] args) {
		try {
		App app = AppSingleton.getInstance();
		app.startApp();	
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
