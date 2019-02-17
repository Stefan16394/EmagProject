package suppliers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import products.Product;
import storage.ProductStorage;

public class Distributor extends Thread {

	private static final int MILLISECONDS_FOR_SLEEP = 2000;
	private Map<Product, Integer> forDelivery = new ConcurrentHashMap<>();
	private ProductStorage productStorage;

	public Distributor(ProductStorage productStorage) {
		this.setDaemon(true);
		this.productStorage = productStorage;
	}

	@Override
	public void run() {
		while (true) {
			while (this.forDelivery.isEmpty()) {
				synchronized (this.productStorage) {
					try {
						this.productStorage.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			try {
				Thread.sleep(MILLISECONDS_FOR_SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Entry<Product, Integer> entry : this.forDelivery.entrySet()) {
				Product product = entry.getKey();
				int quantity = entry.getValue();
				product.increaseQuantity(quantity);
				System.out.println("[Distributor] delivered " + quantity + " of product" + product);
			}
			this.forDelivery.clear();
		}
	}

	public void orderProduct(Product product, int quantity) {
		if (!this.forDelivery.containsKey(product)) {
			this.forDelivery.put(product, quantity);
		} else {
			this.forDelivery.put(product, this.forDelivery.get(product) + quantity);
		}
	}
}
