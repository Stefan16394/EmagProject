package storage;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

import products.Order;
import users.User;

public class OrdersStorage {
	private Map<User, Deque<Order>> orders;
	private Set<Order> forDelivery;

	public OrdersStorage() {
		this.orders = new HashMap<>();
		this.forDelivery = new CopyOnWriteArraySet<>(new TreeSet<Order>((o1, o2) -> o1.getDateOfOrder().compareTo(o2.getDateOfOrder())));
	}

	public void addOrder(User user, Order order) {
		if (!this.orders.containsKey(user)) {
			this.orders.put(user, new ArrayDeque<>());
		}
		this.orders.get(user).push(order);
		this.forDelivery.add(order);
		synchronized (this.forDelivery) {
			this.forDelivery.notifyAll();
		}
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

	public Set<Order> getForDelivery() {
		return this.forDelivery;
	}
}
