package application;

import application.AppSingleton.App;

public class Main {

	public static void main(String[] args) {
		App app = AppSingleton.getInstance();
		app.startApp();	
	}

}
