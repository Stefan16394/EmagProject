package suppliers;

import java.util.Set;

import products.Order;

public class Deliverer implements Runnable {
	private static final int MILLISECONDS_FOR_SLEEP = 10000;
	private Order order;
	
	public Deliverer(Order order) {
		this.order = order;
	}
	
	@Override
	public void run() {
		while(!this.order.isCompleted()) {
			try {
				Thread.sleep(MILLISECONDS_FOR_SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.order.changeOrderStatus();
		}
	}
}
