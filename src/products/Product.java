package products;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Product {
	private static int ID = 1;
	private static final int MIN_RATE = 0;
	private static final int MAX_RATE = 5;
	private int product_id;
	private Map<String, String> characteristics;
	private float price;
	private Set<Integer> rates;
	private int quantity;
	private LocalDate creationDate;

	public Product(float price, int quantity, Map<String, String> characteristics) {
		this.product_id = Product.ID++;
		this.setPrice(price);
		this.setQuantity(quantity);
		this.creationDate = LocalDate.of(new Random().nextInt(4) + 2015, new Random().nextInt(12) + 1,
				new Random().nextInt(28) + 1);
		this.rates = new HashSet<Integer>();
		this.characteristics = characteristics;
		this.rateTheProduct(new Random().nextInt(5) + 1);
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

	@Override
	public String toString() {
		return "Product : price = " +  String.format("%.2f", this.price) + ", id = " + this.product_id + ", rate= " + getRate() + ", quantity = "
				+ quantity + ", creationDate = " + this.creationDate + " Some more characteristics: "
				+ this.listCharacteristics();
	}

	public int getProduct_id() {
		return product_id;
	}
}