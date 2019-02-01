package products;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Product {

	private Map<String, String> characteristics;
	private static final int NUM_OF_RATES = 2;
	private static final int MAX_RATE = 5;
	private float price;
	private Set<Integer> rates;
	private int quantity;
	private LocalDate creationDate;

	public Product(float price, int quantity, Map<String, String> characteristics) {
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

//	public float getRate() {
//		return rate;
//	}

//	public void setRate(float rate) {
//		if(rate > 0  && rate <= MAX_RATE)
//		this.rate = (this.rate + rate) / NUM_OF_RATES;
//	}

	public void rateTheProduct(int rate) {
		if (rate > 0 && rate <= MAX_RATE) {
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
		return "Product : price=" + price + ", rate=" + getRate() + ", quantity=" + quantity+ "creationDate=" + this.creationDate
				+ " Some more characteristics: " + this.listCharacteristics();
	}
}