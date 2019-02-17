package suppliers;

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import products.Order;
import storage.OrdersStorage;

public class DeliverService extends Thread {
	private static final int NUMBER_OF_DELIVERERS = 5;
	private Set<Order> forDelivery;
	private ExecutorService deliverers;

	public DeliverService(Set<Order> forDelivery) {
		this.forDelivery = forDelivery;
		this.deliverers = Executors.newFixedThreadPool(NUMBER_OF_DELIVERERS);
		this.setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			while (this.forDelivery.isEmpty()) {
				synchronized (this.forDelivery) {
					try {
						this.forDelivery.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			for (Order o : this.forDelivery) {
				this.deliverers.submit(new Deliverer(o));
			}
			this.forDelivery.clear();
		}
	}

}
