package products;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import utilities.Helper;
import java.util.Set;

public class Product {
	private static int ID = 1;
	private static final int MIN_RATE = 0;
	private static final int MAX_RATE = 5;
	private static final int MAX_COUNT_OF_DAYS = 28;
	private static final int MIN_COUNT_OF_DAYS = 1;
	private static final int MIN_COUNT_OF_MONTHS = 1;
	private static final int MAX_COUNT_OF_MONTHS = 12;
	private static final int MIN_YEAR = 2015;
	private static final int MAX_YEAR = 2019;
	private int product_id;
	private Map<String, String> characteristics;
	private float price;
	private Set<Integer> rates;
	private int quantity;
	private LocalDate creationDate;
	private String subCategory;

	public Product(String subCategory, float price, int quantity) {
		this.subCategory = subCategory;
		this.product_id = Product.ID++;
		this.setPrice(price);
		this.setQuantity(quantity);
		this.creationDate = LocalDate.of(Helper.generateRandomNumbers(MIN_YEAR, MAX_YEAR), Helper.generateRandomNumbers(MIN_COUNT_OF_MONTHS, MAX_COUNT_OF_MONTHS),
				Helper.generateRandomNumbers(MIN_COUNT_OF_DAYS, MAX_COUNT_OF_DAYS));
		this.rates = new HashSet<Integer>();
		this.characteristics = new HashMap<>();
		this.rateTheProduct(Helper.generateRandomNumbers(MIN_RATE, MAX_RATE));
		this.setCharacteristics();
	}

	

	public void setCharacteristics() {
		String[] laptopModels = { "Acer 13gx", "Toshiba Satelite c855", "Toshiba Satelite E583", "Asus Rogx1337",
				"HP Omen 15dr991", "Lenovo Legion Y639", "Dell G3", "Acer Swift SF114", "Apple MacBook 13" };
		Map<String, Map<String, String>> map = new HashMap<>();
		Map<String, String> laptops = new HashMap<>();
		laptops.put("display", Helper.generateRandomNumbers(11, 15) + "");
		laptops.put("model", laptopModels[Helper.generateRandomNumbers(0, laptopModels.length - 1)]);
		
		map.put(ProductCategories.PhonesTabletsLaptops.valueOf("LAPTOPS").name(), laptops);

		Map<String, String> characterics = map.get(this.subCategory);
		if (characterics != null) {
			for (Entry<String, String> ch : characterics.entrySet()) {
				this.characteristics.put(ch.getKey(), ch.getValue());
			}
		}
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		if (price > 0)
			this.price = price;
	}

	public void rateTheProduct(int rate) {
		if (rate >= MIN_RATE && rate <= MAX_RATE) {
			this.rates.add(rate);
		}
	}

	public float getRate() {
		int sum = 0;
		for (int i : this.rates) {
			sum += i;
		}
		return (float) sum / this.rates.size();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		if (quantity > 0)
			this.quantity = quantity;
	}

	public void increaseQuantity(int quantity) {
		this.quantity += quantity;
	}

	public void decreaseQuantity(int quantity) {
		this.quantity -= quantity;
	}

	private String listCharacteristics() {
		if (this.characteristics == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> characteristic : this.characteristics.entrySet()) {
			sb.append(System.lineSeparator());
			sb.append(characteristic.getKey() + " - " + characteristic.getValue());
		}
		String characteristics = sb.toString();
		if (characteristics == null) {
			characteristics = "";
		}
		return characteristics;
	}

	public String getSubCategory() {
		return this.subCategory;
	}

	@Override
	public String toString() {
		return "Product: subcategory - " + this.getSubCategory() + " price = " + String.format("%.2f", this.price)
				+ ", id = " + this.product_id + ", rate= " + getRate() + ", quantity = " + quantity
				+ ", creationDate = " + this.creationDate + " Some more characteristics: " + this.listCharacteristics();
	}

	public int getProduct_id() {
		return product_id;
	}
}