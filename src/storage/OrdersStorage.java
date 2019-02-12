package storage;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import products.Order;
import users.User;

public class OrdersStorage {
	private Map<User, Deque<Order>> orders;

	public OrdersStorage() {
		this.orders = new HashMap<>();
	}

	public void addOrder(User user, Order order) {
		if (!this.orders.containsKey(user)) {
			this.orders.put(user, new ArrayDeque<>());
		}
		this.orders.get(user).push(order);
		System.out.println("Order created succesfully!");
	}

	public void listUserOrders(User user) {
		if (!this.orders.containsKey(user) || this.orders.get(user).isEmpty()) {
			System.out.println("You have no orders!");
		} else {
			Deque<Order> orders = this.orders.get(user);
			for (Order o : orders) {
				System.out.println(o);
			}
		}
	}
}
