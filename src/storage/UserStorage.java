package storage;

import java.util.HashMap;
import java.util.Map;

import users.User;

public class UserStorage {
	private Map<String, User> users;

	public UserStorage() {
		this.users = new HashMap<String, User>();
	}

	public void registerUser(User user) {
		if (this.users.containsKey(user.getEmail())) {
			System.out.println("User already exist.");
		} else {
			this.users.put(user.getEmail(), user);
			if (!user.isAdmin()) {
				System.out.println("User " + user.getEmail() + " registered successfully!");
			}
		}
	}
	
	public void deleteAccount(User user) {
		this.users.remove(user.getEmail());
		System.out.println("The user is deleted successfully!");
	}

	public User logIn(String email, String password) {
		User user = null;
		if (this.users.containsKey(email)) {
			if (this.users.get(email).getPassword().equals(password)) {
				user = this.users.get(email);
			} else {
				System.out.println("Incorrect password");
			}
		}
		return user;
	}

	public void listUsers() {
		for (String user : this.users.keySet()) {
			System.out.println(user);
		}
	}
}