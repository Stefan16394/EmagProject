package users;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import products.Product;

public class ShoppingCart {
	private Map<Product,Integer> products;
	
	public ShoppingCart() {
		this.products = new HashMap<>();
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
				System.out.println();
			}
		}
	}
	
	
	
}
