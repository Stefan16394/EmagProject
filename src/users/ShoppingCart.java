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
	private static final int MAX_RATE = 5;
	private static final int MIN_RATE = 1;
	private static final int QUANTITY_TO_DELIVER = 10;
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
					int newQuantity = Helper.commandInput();
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
					System.out.println("There is not enough quantity!");
					quantity = this.checkProductQuantity(product);
					if (quantity != 0) {
						product.decreaseQuantity(quantity);
						totalPrice += product.getPrice() * quantity;
						System.out.println(product.getProduct_id() + " is bought successfully!");
					} else {
						this.products.remove(product);
						System.out.println("Product removed from cart");
					}
				} else {
					totalPrice += product.getPrice() * quantity;
					product.decreaseQuantity(quantity);
					System.out.println(product.listCharacteristics() + " is bought successfully\n");
				}
				if (product.getQuantity() == 0) {
					productStorage.getDistributor().orderProduct(product,
							new Random().nextInt(QUANTITY_TO_DELIVER) + 1);
					synchronized (productStorage) {
						productStorage.notifyAll();
					}
				}
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
				System.out.println(entry.getKey() + "The quantity in your cart: " + entry.getValue());
				totalPrice += entry.getKey().getPrice() * entry.getValue();
				System.out.println();
			}
			System.out.println("Total price: " + String.format("%.2f", totalPrice));
		}

	}

	public void addProduct(Product product, int quantity) {
		if (this.products.containsKey(product)) {
			if (product.getQuantity() < this.products.get(product) + quantity) {
				System.out.println("There is not enough quantity to add in your cart!");
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

	public void rateSomeProduct() {
		this.showProductsInTheCart();
		System.out.println("Enter id! Press 0 for exit!");
		int id = Helper.commandInput();
		if (id == 0) {
			return;
		}
		Optional<Product> product = products.keySet().stream().filter(p -> p.getProduct_id() == id).findFirst();
		if (product.isPresent()) {
			Product p = product.get();
			System.out.println("Enter rate (1 - 5): ");
			int newQuantity = Helper.commandInput();
			boolean rating = p.rateTheProduct(newQuantity);
			if(rating) {
				System.out.println("Thank you for rating the product!");
			} else {
				System.out.println("Invalid rate!");
			}
		} else {
			System.out.println("Wrong id!");
		}

	}

	private int checkProductQuantity(Product product) {
		System.out.println("You can add the available quantity - " + product.getQuantity());
		System.out.println("Do you want to? Press 1 for yes or 2 for no: ");
		Scanner sc = new Scanner(System.in);
		String answer = sc.nextLine();
		if (answer.equals("1")) {
			return product.getQuantity();
		}
		return 0;
	}
}
