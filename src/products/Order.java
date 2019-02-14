package products;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Order {
	private Map<Product, Integer> products;
	private float totalPrice;
	private OrderStatus orderStatus;
	private LocalDate dateOfOrder;

	public Order(Map<Product, Integer> products, float totalPrice) {
		this.products = new HashMap<>(products);
		this.totalPrice = totalPrice;
		this.dateOfOrder = LocalDate.now();
		this.orderStatus = OrderStatus.NOT_STARTED;
	}

	private enum OrderStatus {
		NOT_STARTED, IN_PROCESS_OF_DELIVERY, DELIVERED
	}

	public Map<Product, Integer> getProducts() {
		return Collections.unmodifiableMap(this.products);
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public LocalDate getDateOfOrder() {
		return dateOfOrder;
	}

	public boolean isCompleted() {
		return this.orderStatus.equals(OrderStatus.DELIVERED);
	}

	public void changeOrderStatus() {
		OrderStatus[] o = OrderStatus.values();
		for (int i = 0; i < o.length; i++) {
			if (o[i].equals(this.orderStatus) && (i + 1) < o.length) {
				System.out.println(o[i + 1]);
				this.orderStatus = o[i + 1];
				break;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("------------").append("\n");
		sb.append("Products:\n");
		for (Entry<Product, Integer> entry : this.products.entrySet()) {
			sb.append(entry.getKey().getProduct_id() + " - " + entry.getValue());
			sb.append("\n");
		}
		sb.append("Total Price: " + totalPrice).append("\n");
		sb.append("Order Status: " + this.orderStatus).append("\n");
		sb.append("Date of order: " + this.dateOfOrder).append("\n");
		sb.append("------------").append("\n");

		return sb.toString();
	}
}
