package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import products.CategoryStorage;
import products.Product;
import products.ProductCategories;
import storage.ProductStorage;
import users.Message;
import users.User;
import utilities.Helper;

public class ProductService {
	private static final int QUATITY = 20;
	private static final int MAX_PRICE = 100;
	private static Scanner sc = new Scanner(System.in);;
	private CategoryStorage categoryStorage;
	private ProductStorage productStorage;

	public ProductService() {
		this.categoryStorage = new CategoryStorage();
	}

	public Message createProduct() {
		System.out.println("Choose category:");
		for (Entry<Integer, ProductCategories> category : this.categoryStorage.getCategories().entrySet()) {
			System.out.println(category.getKey() + " - " + category.getValue());
		}
		int input = Helper.commandInput();

		if (!this.categoryStorage.getCategories().containsKey(input)) {
			System.out.println("Invalid entry. Please try again.");
		} else {
			ProductCategories category = this.categoryStorage.getCategories().get(input);
			if (category != null) {
				System.out.println(category);
			}
			Map<Integer, String> subCategories = this.categoryStorage.getSubCategories().get(category);
			for (Entry<Integer, String> entry : subCategories.entrySet()) {
				System.out.println(entry.getKey() + " - " + entry.getValue());
			}
			int input2 = Helper.commandInput();
			String subCat = subCategories.get(input2);
			if (subCat != null) {
				System.out.println(subCat);
				float price = new Random().nextFloat() * MAX_PRICE;
				int quantity = new Random().nextInt(QUATITY) + 1;
				Map<String, String> characteristics = new HashMap<>();
				while (true) {
					System.out.println("Please enter some characteristics:");
					System.out.println("To exit press '0'!\n");
					System.out.println("Enter characteristic name:");
					String characteristic = sc.nextLine();
					if (characteristic.equals("0")) {
						break;
					}
					System.out.println("Enter characteristic value:");

					String value = sc.nextLine();
					if (value.equals("0")) {
						break;
					} else {
						if ((characteristic != null && value != null)
								&& (characteristic.trim().length() != 0 && value.trim().length() != 0))
							characteristics.put(characteristic, value);
					}
				}
				Product product = new Product(subCat, price, quantity, characteristics);
				this.productStorage.addProduct(category, subCat, product);
				System.out.println("The product " + product + "was added!");
				return new Message(
						"Hello! Come to our site and see our new amazing and fantastic product: \n  " + product);
			} else {
				System.out.println(("Invalid entry. Please try again."));
			}
		}
		return null;
	}

	private ProductCategories chooseCategory() {
		while (true) {
			System.out.println("Choose category:");
			for (Entry<Integer, ProductCategories> category : this.categoryStorage.getCategories().entrySet()) {
				System.out.println(category.getKey() + " - " + category.getValue());
			}
			int input = Helper.commandInput();
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
			int input = Helper.commandInput();
			String subCategory = subCategories.get(input);
			if (subCategory == null) {
				System.out.println("There is no such subcategory!");
			} else {
				return subCategory;
			}
		}
	}

	public void findProductsByCategory(User user) {

		ProductCategories category = chooseCategory();

		String subCategory = chooseSubcategory(category);

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
		List<Product> products = this.productStorage.findProductsByCategoryAndSubcategory(category, subCategory)
				.stream().sorted(comparator).collect(Collectors.toList());

		while (true) {
			System.out.println("Products in category " + subCategory + ":");
			for (Iterator<Product> it = products.iterator(); it.hasNext();) {
				Product product = it.next();
				System.out.println(product.getProduct_id() + " - " + product);
			}

			System.out.println("Choose product by id:");
			System.out.println("For exit press '0'");
			int id = Helper.commandInput();
			if (id == 0) {
				return;
			}
			Optional<Product> product = products.stream().filter(p -> p.getProduct_id() == id).findFirst();
			if (product.isPresent()) {
				System.out.println("Do you want to add in your cart? Press 'y' for yes.");
				String answer = sc.nextLine();
				if (answer.equals("y")) {
					if (user != null) {
						System.out.println("Enter quantity:");
						int quantity = Helper.commandInput();
						if (quantity == 0) {
							continue;
						}
						user.getCart().addProduct(product.get(), quantity);
						System.out.println("You added the product successfully!\n");
					} else {
						System.out.println("You need to log in first!\n");
						break;
					}
				} else {
					System.out.println("OK! Have a nice day!\n");
				}
			} else {
				System.out.println("This product doesnt exist!\n");
			}
		}
	}

	public void generateRandomProducts() {

		final String laptops = "[{\"characteristics\":[{\"model\":\"Asus\"},{\"display\":\"12\"},{\"memory\":\"8Gb\"}]},{\"characteristics\":[{\"model\":\"Toshiba\"},{\"display\":\"13\"},{\"memory\":\"4Gb\"}]}]";
		final String tablets = "[{\"characteristics\":[{\"model\":\"Huawei Media Pad\"},{\"display\":\"7.7\\\"\"},{\"memory\":\"32Gb\"},{\"camera\":\"6mpx\"},{\"battery\":\"4600mAh\"}]},{\"characteristics\":[{\"model\":\"Samsung Galaxy Tab ET560\"},{\"display\":\"8\\\"\"},{\"memory\":\"50Gb\"},{\"camera\":\"5mpx\"},{\"battery\":\"5600mAh\"}]},{\"characteristics\":[{\"model\":\"Apple Ipad\"},{\"display\":\"9\\\"\"},{\"memory\":\"46Gb\"},{\"camera\":\"8mpx\"},{\"battery\":\"4600mAh\"}]},{\"characteristics\":[{\"model\":\"Lenovo Tab YT-3\"},{\"display\":\"4.5\\\"\"},{\"memory\":\"32Gb\"},{\"camera\":\"9mpx\"},{\"battery\":\"3600mAh\"}]},{\"characteristics\":[{\"model\":\"Acer Tab\"},{\"display\":\"5.5\\\"\"},{\"memory\":\"20Gb\"},{\"camera\":\"3mpx\"},{\"battery\":\"3200mAh\"}]}]";
		final String phones = "[{\"characteristics\":[{\"model\":\"Samsung Galaxy S9\"},{\"display\":\"6\\\"\"},{\"camera\":\"8mpx\"}]},{\"characteristics\":[{\"model\":\"IPhone 7\"},{\"display\":\"6\\\"\"},{\"camera\":\"12mpx\"}]},{\"characteristics\":[{\"model\":\"Huwaei P20\"},{\"display\":\"5.5\\\"\"},{\"camera\":\"10mpx\"}]},{\"characteristics\":[{\"model\":\"Nokia 2\"},{\"display\":\"5\\\"\"},{\"camera\":\"8mpx\"}]},{\"characteristics\":[{\"model\":\"Xiaomi Redmi\"},{\"display\":\"6\\\"\"},{\"camera\":\"5mpx\"}]}]";
		final String tv = "[{\"characteristics\":[{\"model\":\"Smart Android LED Star-Light\"},{\"display\":\"32\"}]},{\"characteristics\":[{\"model\":\"LED Smart LG\"},{\"display\":\"36\"}]},{\"characteristics\":[{\"model\":\"LED Smart Toshiba\"},{\"display\":\"36\"}]},{\"characteristics\":[{\"model\":\"LED Smart Samsung\"},{\"display\":\"55\"}]},{\"characteristics\":[{\"model\":\"LED Smart Horizon\"},{\"display\":\"49\"}]}]";
		final String audio = "[{\"characteristics\":[{\"model\":\"Hi-Fi Panasonic AKX200E\"},{\"power\":\"400W\"}]},{\"characteristics\":[{\"model\":\"Sony SHAKE-X30 High Power\"},{\"power\":\"500W\"}]},{\"characteristics\":[{\"model\":\"Sony MHCV11\"},{\"power\":\"450W\"}]},{\"characteristics\":[{\"model\":\"HAkai, AHT-38A5E\"},{\"power\":\"600W\"}]},{\"characteristics\":[{\"model\":\"Panasonic SC-PM250EG-K\"},{\"power\":\"300W\"}]}]";
		final String electronics = "[{\"characteristics\":[{\"model\":\"Refrigerator Star-Light FDDV-213A+\"},{\"color\":\"pink\"}]},{\"characteristics\":[{\"model\":\"Vacuum cleaner Samsung\"},{\"power\":\"500W\"}]},{\"characteristics\":[{\"model\":\" Refrigerator Electrolux EJ2801AOX2\"},{\"color\":\"black\"}]},{\"characteristics\":[{\"model\":\"Washing machine Electrolux EW6S427W \"},{\"power\":\"600W\"}]},{\"characteristics\":[{\"model\":\"Washing ELITE WM-80567G\"},{\"power\":\"400W\"}]}]";
		final String photo = "[{\"characteristics\":[{\"model\":\"DSLR Canon EOS 2000D\"},{\"camera\":\"24mpx\"}]},{\"characteristics\":[{\"model\":\"DSLR Nikon D7200\"},{\"camera\":\"30mpx\"}]},{\"characteristics\":[{\"model\":\" DSLR Nikon D5300\"},{\"camera\":\"20mpx\"}]},{\"characteristics\":[{\"model\":\"DSLR Canon EOS 200D\"},{\"camera\":\"15mpx\"}]},{\"characteristics\":[{\"model\":\"DSLR Nikon D3500\"},{\"camera\":\"20mpx\"}]}]";

		final String parfumes = "[{\"characteristics\":[{\"name\":\"Parfume Beyonce Pulse\"},{\"ml\":\"50\"}]},{\"characteristics\":[{\"name\":\" Christina Aguilera\"},{\"ml\":\"75\"}]},{\"characteristics\":[{\"name\":\"Calvin Klein Euphoria\"},{\"ml\":\"30\"}]},{\"characteristics\":[{\"name\":\"Versace Crystal Noir\"},{\"ml\":\"90\"}]},{\"characteristics\":[{\"name\":\" Guerlain Mon Guerlain\"},{\"ml\":\"100\"}]}]";
		final String makeUp = "[{\"characteristics\":[{\"name\":\"Nyx Total Contol\"},{\"ml\":\"50\"}]},{\"characteristics\":[{\"name\":\" L'Oreal Paris True Match \"},{\"ml\":\"30\"}]},{\"characteristics\":[{\"name\":\"Maybelline NY Lash\"},{\"ml\":\"15\"}]},{\"characteristics\":[{\"name\":\"Christian Dior Diorshow\"},{\"ml\":\"10\"}]},{\"characteristics\":[{\"name\":\"Lipstick Kylie Birthday Edition\"},{\"color\":\"red\"}]}]";
		final String cosmetic = "[{\"characteristics\":[{\"name\":\"Nivea \"},{\"ml\":\"250\"}]},{\"characteristics\":[{\"name\":\"Old Spice Lagoon \"},{\"ml\":\"30\"}]},{\"characteristics\":[{\"name\":\"Scholl Fresh Step\"},{\"ml\":\"250\"}]},{\"characteristics\":[{\"name\":\"L'Oreal Professional\"},{\"ml\":\"500\"}]},{\"characteristics\":[{\"name\":\"Pantene Pro-V Aqualight\"},{\"ml\":\"500\"}]}]";
		final String accessories = "[{\"characteristics\":[{\"name\":\"Giga Dreams Makeup Box \"}]},{\"characteristics\":[{\"name\":\"Bielenda \"},{\"ml\":\"30\"}]},{\"characteristics\":[{\"name\":\"Giga Dreams 4\"}]},{\"characteristics\":[{\"name\":\"Black Head mask\"}]},{\"characteristics\":[{\"name\":\"Star Colors \"},{\"ml\":\"500\"}]}]";

		final String desktop_computers_monitors = "[{\"characteristics\":[{\"model\":\"Gaming Serioux \"},{\"Processor\":\"Intel Core i3 2.5GHZ\\\"\"},{\"RAM memory\":\"32GB\"}]},{\"characteristics\":[{\"model\":\"PC MSI Nightblade MI3 \"},{\"Processor\":\"Intel Core i3 2.6GHZ\\\"\"},{\"RAM memory\":\"32GB\"}]},{\"characteristics\":[{\"model\":\"LED Dell UltraSharp \"},{\"display\":\"38\\\"\"}]},{\"characteristics\":[{\"model\":\"LED IPS LG  \"},{\"display\":\"25\\\"\"}]},{\"characteristics\":[{\"model\":\"LED IPS Dell  \"},{\"display\":\"28\\\"\"}]}]";
		final String pc_components = "[{\"characteristics\":[{\"model\":\" Intel Core i5-9400F \"},{\"cores\":\"4\"}]},{\"characteristics\":[{\"model\":\" Intel® Core™ i5-8400 \"},{\"cores\":\"8\"}]},{\"characteristics\":[{\"model\":\" ASUS Phoenix GeForce® GTX 1050\"},{\"memory\":\"2GB\"}]},{\"characteristics\":[{\"model\":\" MANLI GeForce GTX1060\"},{\"memory\":\"3GB\"}]},{\"characteristics\":[{\"model\":\"MSI RADEON™ RX 580 \"},{\"memory\":\"8GB\"}]}]";
		final String systems = "[{\"characteristics\":[{\"name\":\" Microsoft Windows 10 Home \"},{\"architecture\":\"64\"}]},{\"characteristics\":[{\"name\":\" Microsoft Windows 7 Pro \"},{\"architecture\":\"32\"}]},{\"characteristics\":[{\"name\":\" Microsoft Windows 8 Enterprise \"},{\"architecture\":\"64\"}]},{\"characteristics\":[{\"name\":\" Microsoft Windows 8.1 Proffessional \"},{\"architecture\":\"32\"}]},{\"characteristics\":[{\"name\":\" Microsoft Windows XP \"},{\"architecture\":\"64\"}]}]";

		final String[] jsons = new String[] { phones, tablets, laptops, tv, audio, electronics, photo, parfumes, makeUp,
				cosmetic, accessories, desktop_computers_monitors, pc_components, systems };
		int c = 0;
		for (Entry<ProductCategories, Map<Integer, String>> s : this.categoryStorage.getSubCategories().entrySet()) {
			ProductCategories category = s.getKey();
//			System.out.println(category);

			for (String subC : s.getValue().values()) {
				String json = jsons[c++];
				Data[] data = new Gson().fromJson(json, Data[].class);
				for (Data d : data) {
					List<Object> characteristics = d.characteristics;
					Map<String, String> productCharacteristics = new LinkedHashMap<String, String>();

					for (Object obj : characteristics) {
						String[] x = obj.toString().replace("{", "").replace("}", "").split("=");
						String o = x[0];
						String o1 = x[1];
						productCharacteristics.put(o, o1);
					}
					float price = new Random().nextFloat() * MAX_PRICE;
					int quantity = new Random().nextInt(QUATITY) + 1;
					Product product = new Product(subC, price, quantity, productCharacteristics);
					productStorage.addProduct(category, subC, product);
				}
			}
		}
	}

	public void checkShoppingCart(User user) {
		while (true) {
			System.out.println("Enter command: 1 - Show all products 2 - Buy all products 3 - Remove product "
					+ "4 - Change the quantity of some product 5 - List My Orders 6 - Rate some product 7 - Exit");
			String command = sc.nextLine();
			switch (command) {
			case "1":
				user.getCart().showProductsInTheCart();
				break;
			case "2":
				user.getCart().buyAllProducts(this.productStorage, user);
				break;
			case "3":
				user.getCart().removeProduct();
				break;
			case "4":
				user.getCart().changeQuantityOfProduct();
				break;
			case "5":
				this.productStorage.listUserOrders(user);
				break;
			case "6":
				user.getCart().rateSomeProduct();
				break;
			default:
				return;
			}
		}
	}

	public void setProductStorage(ProductStorage productStorage) {
		this.productStorage = productStorage;
	}

}

class Data {
	List<Object> characteristics;
}
