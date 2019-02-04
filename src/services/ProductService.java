package services;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import comparators.DateComparator;
import comparators.PriceAscendingComparator;
import comparators.PriceDescendingComparator;
import comparators.RateComparator;

import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import products.CategoryStorage;
import products.Product;
import products.ProductCategories;
import storage.ProductStorage;
import users.User;

public class ProductService {
	private CategoryStorage categoryStorage;
	private static Scanner sc = new Scanner(System.in);;

	public ProductService() {
		this.categoryStorage = new CategoryStorage();
	}

	public void createProduct(ProductStorage productStorage) {
		System.out.println("Choose category:");
		for (Entry<Integer, ProductCategories> category : this.categoryStorage.getCategories().entrySet()) {
			System.out.println(category.getKey() + " - " + category.getValue());
		}
		int input = sc.nextInt();

		if (!this.categoryStorage.getCategories().containsKey(input)) {
			System.out.println(("Invalid entry. Please try again."));
		} else {
			ProductCategories category = this.categoryStorage.getCategories().get(input);
			if (category != null) {
				System.out.println(category);
			}
			Map<Integer, String> subCategories = categoryStorage.getSubCategories().get(category);
			for (Entry<Integer, String> entry : subCategories.entrySet()) {
				System.out.println(entry.getKey() + " - " + entry.getValue());
			}
			int input2 = sc.nextInt();
			String subCat = subCategories.get(input2);
			if (subCat != null) {
				System.out.println(subCat);

				float price = new Random().nextFloat() * 100;
				int quantity = new Random().nextInt(20) + 1;
				Product product = new Product(price, quantity, null);
				System.out.println(product);
				productStorage.addProduct(category, subCat, product);
			} else {
				System.out.println(("Invalid entry. Please try again."));
			}
		}
	}

	private ProductCategories chooseCategory() {
		while (true) {
			System.out.println("Choose category:");
			for (Entry<Integer, ProductCategories> category : this.categoryStorage.getCategories().entrySet()) {
				System.out.println(category.getKey() + " - " + category.getValue());
			}
			int input = sc.nextInt();
			ProductCategories category = this.categoryStorage.getCategories().get(input);
			if (category == null) {
				System.out.println("There is no such category!");
			} else {
				return category;
			}
		}
	}

	private String chooseSubcategory(ProductCategories category) {
		while (true) {
			Map<Integer, String> subCategories = categoryStorage.getSubCategories().get(category);
			for (Entry<Integer, String> entry : subCategories.entrySet()) {
				System.out.println(entry.getKey() + " - " + entry.getValue());
			}
			System.out.println("Choose subcategory:");
			int input2 = sc.nextInt();
			String subCategory = subCategories.get(input2);
			if (subCategory == null) {
				System.out.println("There is no such subcategory!");
			} else {
				return subCategory;
			}
		}
	}

	public void findProductsByCategory(ProductStorage storage, User user) {

		ProductCategories category = chooseCategory();

		String subCategory = chooseSubcategory(category);

		sc.nextLine();
		System.out.println("Choose sort criteria:");
		System.out.println("1 -> price - ascending order");
		System.out.println("2 -> price - descending order");
		System.out.println("3 -> from newest to oldest products");
		System.out.println("4 -> rate");
		String criteria = sc.nextLine();

		Comparator<Product> comparator = new PriceAscendingComparator();
		switch (criteria) {
		case ("2"):
			comparator = new PriceDescendingComparator();
			break;
		case ("3"):
			comparator = new DateComparator();
			break;
		case ("4"):
			comparator = new RateComparator();
			break;
		}
		List<Product> products = storage.findProductsByCategorieAndSubcategory(category, subCategory).stream()
				.sorted(comparator).collect(Collectors.toList());

		

		while (true) {
			System.out.println("Products in category " + subCategory + ":");
			for (Iterator<Product> it = products.iterator(); it.hasNext();) {
				Product product = it.next();
				System.out.println(product.getProduct_id() + " - " + product);
			}
			
			System.out.println("Choose product by id:");
			System.out.println("For exit press '0'");
			int id = sc.nextInt();
			sc.nextLine();
			if(id == 0) {
				return;
			}
			Optional<Product> product = products.stream().filter(p -> p.getProduct_id() == id).findFirst();
			if (product.isPresent()) {
				System.out.println("Do you want to add in your cart? Press 'y' for yes.");
				String answer = sc.nextLine();
				if (answer.equals("y")) {
					if (user != null) {
						System.out.println("Enter quantity:");
						int quantity = sc.nextInt();
						user.getCart().addProduct(product.get(), quantity);
					} else {
						System.out.println("You need to log in first!");
						break;
					}
				} else {
					System.out.println("OK! Have a nice day!");
				}
			} else {
				System.out.println("This product doesnt exist");
			}
		}
	}

	public void generateRandomProducts(ProductStorage productStorage) {
		for (Entry<ProductCategories, Map<Integer, String>> s : this.categoryStorage.getSubCategories().entrySet()) {
			ProductCategories category = s.getKey();
			System.out.println(category);
			for (String subC : s.getValue().values()) {
				System.out.println(subC);
				for (int i = 0; i < 5; i++) {
					System.out.println("\nTo terminate creadting products press 'r'\n");
					float price = new Random().nextFloat() * 100;
					int quantity = new Random().nextInt(20) + 1;
					System.out.println("Entry some characteristics for the product of category " + category
							+ ", subcategory " + subC + ":\n");
					Map<String, String> characteristics = new HashMap<String, String>();
//					while (true) {
//						System.out.println("To exit press '0'!\n");
//						String s1 = sc.nextLine();
//						if (s1.equals("r")) {
//							return;
//						}
//						if (s1.equals("0")) {
//							break;
//						}
//						String s2 = sc.nextLine();
//						if (s2.equals("0")) {
//							break;
//						} else {
//							if ((s1 != null && s2 != null) && (s1.trim().length() != 0 && s2.trim().length() != 0))
//								characteristics.put(s1, s2);
//						}
//					}
					Product product = new Product(price, quantity, Collections.unmodifiableMap(characteristics));
					productStorage.addProduct(category, subC, product);
				}
			}
			System.out.println("-----");
		}
	}
}
