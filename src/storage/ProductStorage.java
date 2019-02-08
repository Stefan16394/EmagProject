package storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import products.Order;
import products.Product;
import products.ProductCategories;
import suppliers.Distributor;
import users.User;

public class ProductStorage {
	private Map<ProductCategories, Map<String, Set<Product>>> products;
	private OrdersStorage ordersStorage;
	private Distributor distributor;

	public ProductStorage() {
		this.products = new TreeMap<ProductCategories, Map<String, Set<Product>>>();
		this.ordersStorage = new OrdersStorage();
	}

	public void addProduct(ProductCategories category, String subCategory, Product product) {
		if (!this.products.containsKey(category)) {
			this.products.put(category, new TreeMap<String, Set<Product>>());
		}
		if (!this.products.get(category).containsKey(subCategory)) {
			this.products.get(category).put(subCategory, new HashSet<Product>());
		}

		this.products.get(category).get(subCategory).add(product);
		System.out.println("The product " + product + " is added successfully to category " + category + " subcategory "
				+ subCategory);

	}

	public void listAllProducts() {
		for (Entry<ProductCategories, Map<String, Set<Product>>> entry : this.products.entrySet()) {
			System.out.println(entry.getKey());
			for (Entry<String, Set<Product>> sub : entry.getValue().entrySet()) {
				System.out.println("-" + sub.getKey());
				sub.getValue().forEach(pr -> System.out.println(pr));
			}
		}
	}
	
	public void addOrder(User user,Order orders) {
		this.ordersStorage.addOrder(user, orders);
	}
	
	public void listUserOrders(User user) {
		this.ordersStorage.listUserOrders(user);
	}

	public Set<Product> findProductsByCategoryAndSubcategory(ProductCategories category, String subCategory) {
		return this.products.get(category).get(subCategory);
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}
}
