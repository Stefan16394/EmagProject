package suppliers;

import java.util.Set;

import products.Order;

public class Deliverer implements Runnable {
	private Order order;
	
	public Deliverer(Order order) {
		this.order = order;
	}
	
	@Override
	public void run() {
		while(!this.order.isCompleted()) {
			System.out.println(Thread.currentThread().getName());
			int numberOfProducts = this.order.getProducts().size();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.order.changeOrderStatus();
		}
	}
}
