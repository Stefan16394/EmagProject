package products;

public class Product {

	private static final int NUM_OF_RATES = 2;
	private static final int MAX_RATE = 5;
	private float price;
	private float rate;
	private int quantity;

	public Product(float price, int quantity) {
		this.setPrice(price);
		this.setQuantity(quantity);
	}

	public float getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Product [price=" + price + ", rate=" + rate + ", quantity=" + quantity + "]";
	}

	public void setPrice(float price) {
		if(price > 0)
			this.price = price;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		if(rate > 0  && rate <= MAX_RATE)
		this.rate = (this.rate + rate) / NUM_OF_RATES;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		if(quantity > 0)
			this.quantity = quantity;
	}

	
	
	

}
