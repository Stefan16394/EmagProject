package users;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.Scanner;

import products.Product;

public class ShoppingCart {
	private Map<Product,Integer> products;
	private Scanner sc = new Scanner(System.in);
	
	public ShoppingCart() {
		this.products = new HashMap<>();
	}
	
	public void removeProduct() {
		while(true) {
			showProductsInTheCart();
			System.out.println("Enter id! Press 0 for exit!");
			int id = sc.nextInt();
			if(id == 0) {
				return;
			}
			Optional<Product> product = products.keySet().stream().filter(p -> p.getProduct_id() == id).findFirst();
			if(product.isPresent()) {
				this.products.remove(product.get());
				System.out.println(product.get().getProduct_id()+" removed successfully!");
			}else {
				System.out.println("Wrong id!");
			}
		}
	}
	
	public void buyAllProducts() {
		for(Entry<Product,Integer> entry:this.products.entrySet()) {
			Product product = entry.getKey();
			int quantity = entry.getValue();
			
			if(product.getQuantity() < quantity) {
				System.out.println("Nqmame dostatychno nalichnost!");
				quantity = product.getQuantity();
				System.out.println("Imame " + quantity + "Jelaete li da zakupite? Natisnete 'y' za da");
				String answer = sc.nextLine();
				if(!answer.equals("y")) {
					System.out.println("OK! Have a nice day!");
				} 
			} else {
				product.decreaseQuantity(quantity);
				System.out.println(product.getProduct_id()+" e kupen uspeshno!");
			}
			
			
			
//			if(product.getQuantity()>=quantity) {
//				product.decreaseQuantity(quantity);
//			}else {
//				System.out.println("Nqmame dostatychno nalichnost!");
//				quantity = product.getQuantity();
//				System.out.println("Imame " + quantity + "Jelaete li da zakupite? Natisnete 'y' za da");
//				String answer = sc.nextLine();
//				if(answer.equals("y")) {
//					product.decreaseQuantity(quantity); // nishkata
//				} 
//			}
		}
		this.products.clear();
	}
	
	public void showProductsInTheCart() {
		if(this.products.isEmpty()) {
			System.out.println("Your shopping cart is empty!");
		}else {
			for(Entry<Product, Integer> entry : this.products.entrySet()) {
				System.out.println(entry.getKey() + " " + entry.getValue());
			}
		}	
	}
	
	public void addProduct(Product product,int quantity) {
		if(this.products.containsKey(product)) {
			if(product.getQuantity() < this.products.get(product) + quantity) {
				System.out.println("Nqmame dostatychno nalichnost za da dobavite kym porychkata si!");
				if(this.products.get(product)+quantity - product.getQuantity() > 0) {
					System.out.println("Mojete da dobavite samo nalichnoto kolichestvo");
					System.out.println("Jelatete li? Napishete 1 za da i 2 za ne");
					Scanner sc = new Scanner(System.in);
					String answer = sc.nextLine();
					if(answer.equals("1")) {
						quantity = product.getQuantity();
					} else {
						quantity = 0;
					}
				}
			} 
			this.products.put(product, this.products.get(product)+quantity);
		}else {
			if(product.getQuantity() >= quantity) {
				this.products.put(product, quantity);
			} else {
				System.out.println("Mojete da dobavite samo nalichnoto kolichestvo");
				System.out.println("Jelatete li? Napishete 1 za da i 2 za ne");
				Scanner sc = new Scanner(System.in);
				String answer = sc.nextLine();
				if(answer.equals("1")) {
					quantity = product.getQuantity();
					this.products.put(product, quantity);
				} 
			}
		}
	}
}
