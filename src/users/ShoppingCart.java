package users;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Scanner;

import products.Order;
import products.Product;
import storage.ProductStorage;
import utilities.Helper;

public class ShoppingCart {
	private Map<Product, Integer> products;
	private Scanner sc = new Scanner(System.in);

	public ShoppingCart() {
		this.products = new HashMap<>();
	}

	public void removeProduct() {
		if (this.products.isEmpty()) {
			System.out.println("Your shopping cart is empty!");
		} else {
			while (true) {
				showProductsInTheCart();
				System.out.println("Enter id! Press 0 for exit!");
				int id = Helper.commandInput();
				if (id == 0) {
					return;
				}
				Optional<Product> product = products.keySet().stream().filter(p -> p.getProduct_id() == id).findFirst();
				if (product.isPresent()) {
					this.products.remove(product.get());
					System.out.println(product.get().getProduct_id() + " removed successfully!");
				} else {
					System.out.println("Wrong id!");
				}
			}
		}
	}

	public void changeQuantityOfProduct() {
		if (this.products.isEmpty()) {
			System.out.println("Your shopping cart is empty!");
		} else {
			while (true) {
				showProductsInTheCart();
				System.out.println("Enter id! Press 0 for exit!");
				int id = Helper.commandInput();
				if (id == 0) {
					return;
				}
				Optional<Product> product = products.keySet().stream().filter(p -> p.getProduct_id() == id).findFirst();
				if (product.isPresent()) {
					Product p = product.get();
					System.out.println("Enter new quantity: ");
					int newQuantity = sc.nextInt();
					if (p.getQuantity() < newQuantity) {
						newQuantity = this.checkProductQuantity(p);
					}
					if (newQuantity > 0) {
						this.products.put(p, newQuantity);
						System.out.println("Quantity succesfully changed to " + newQuantity);
					} else {
						System.out.println("Invalid quantity");
					}
				} else {
					System.out.println("Wrong id!");
				}
			}
		}

	}

	public void buyAllProducts(ProductStorage productStorage, User user) {
		float totalPrice = 0;
		if (this.products.isEmpty()) {
			System.out.println("Your shopping cart is empty!");
		} else {
			for (Entry<Product, Integer> entry : this.products.entrySet()) {
				Product product = entry.getKey();
				int quantity = entry.getValue();

				if (product.getQuantity() < quantity) {
					System.out.println("Nqmame dostatychno nalichnost!");
					quantity = this.checkProductQuantity(product);
					if (quantity != 0) {
						product.decreaseQuantity(quantity);
						totalPrice += product.getPrice() * quantity;
						System.out.println(product.getProduct_id() + " e kupen uspeshno!");
					} else {
						this.products.remove(product);
						System.out.println("Product removed from cart");
					}
				} else {
					totalPrice += product.getPrice() * quantity;
					product.decreaseQuantity(quantity);
					System.out.println(product.getProduct_id() + " e kupen uspeshno!");
				}
				if (product.getQuantity() == 0) {
					productStorage.getDistributor().orderProduct(product, new Random().nextInt(10) + 1);
					synchronized (productStorage) {
						productStorage.notifyAll();
					}
				}
				System.out.println(product);
			}
			Order order = new Order(this.products, totalPrice);
			productStorage.addOrder(user, order);

		}
		this.products.clear();
	}

	public void showProductsInTheCart() {
		float totalPrice = 0;
		if (this.products.isEmpty()) {
			System.out.println("Your shopping cart is empty!");
		} else {
			for (Entry<Product, Integer> entry : this.products.entrySet()) {
				System.out.println(entry.getKey() + " " + entry.getValue());
				totalPrice += entry.getKey().getPrice() * entry.getValue();
			}
			System.out.println("Total price: " + String.format("%.2f", totalPrice));
		}

	}

	public void addProduct(Product product, int quantity) {
		if (this.products.containsKey(product)) {
			if (product.getQuantity() < this.products.get(product) + quantity) {
				System.out.println("Nqmame dostatychno nalichnost za da dobavite kym porychkata si!");
				if (this.products.get(product) + quantity - product.getQuantity() > 0) {
					quantity = this.checkProductQuantity(product);
				}
			}
			this.products.put(product, this.products.get(product) + quantity);
		} else {
			if (product.getQuantity() >= quantity) {
				this.products.put(product, quantity);
			} else {
				quantity = this.checkProductQuantity(product);
				if (quantity != 0) {
					this.products.put(product, quantity);
				}
			}
		}
	}

	private int checkProductQuantity(Product product) {
		System.out.println("Mojete da dobavite samo nalichnoto kolichestvo - " + product.getQuantity());
		System.out.println("Jelatete li? Napishete 1 za da i 2 za ne");
		Scanner sc = new Scanner(System.in);
		String answer = sc.nextLine();
		if (answer.equals("1")) {
			return product.getQuantity();
		}
		return 0;
	}

}
