package storage;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import products.Product;
import products.ProductCategories;

public class ProductStorage {
	private Map<ProductCategories, Map<String,Set<Product>>> products;
	
	public ProductStorage() {
		this.products = new TreeMap<>();
	}
	
	public void addProduct(ProductCategories category,String subCategory,Product product) {
		if(!this.products.containsKey(category)) {
			this.products.put(category, new TreeMap<>());
		}
		if(!this.products.get(category).containsKey(subCategory)) {
			this.products.get(category).put(subCategory, new HashSet<Product>());
		}
		
		this.products.get(category).get(subCategory).add(product);
		System.out.println("The product is added successfully!");
		
	}
}
