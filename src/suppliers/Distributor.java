package suppliers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import products.Product;
import storage.ProductStorage;

public class Distributor extends Thread {

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
				System.out.println("Distrubutor is here!");
				synchronized (this.productStorage) {
					try {
						this.productStorage.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
