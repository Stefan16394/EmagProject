package services;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import products.CategorieStorage;
import products.Product;
import products.ProductCategories;
import storage.ProductStorage;

public class ProductService {
	private CategorieStorage categoryStorage;
	private Scanner sc;

	public ProductService() {
		this.sc = new Scanner(System.in);
		this.categoryStorage = new CategorieStorage();
	}

	public void createProduct(ProductStorage productStorage) {
		System.out.println("Choose category:");
		for (Entry<Integer, ProductCategories> category : this.categoryStorage.getCategories().entrySet()) {
			System.out.println(category.getKey() + " - " + category.getValue());
		}
		int input = sc.nextInt();
		ProductCategories category = this.categoryStorage.getCategories().get(input);
		if (category != null) {
			System.out.println(category);
		}
		Map<Integer,String> subCategories = categoryStorage.getSubCategories().get(category);
		for(Entry<Integer, String> e:subCategories.entrySet()) {
			System.out.println(e.getKey() + " - " + e.getValue());
		}
		int input2=sc.nextInt();
		String subCat = subCategories.get(input2);
		System.out.println(subCat);
		
		float price = new Random().nextFloat()*100;
		int quantity = new Random().nextInt(20) + 1;
		Product product = new Product(price, quantity);
		System.out.println(product);
		productStorage.addProduct(category, subCat, product);
		
	}
}
