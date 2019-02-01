package comparators;

import java.util.Comparator;

import products.Product;

public class PriceDescendingComparator implements Comparator<Product> {
	
	@Override
	public int compare(Product p1, Product p2) {
		int result = (int) (p2.getPrice() - p1.getPrice());
		if (result == 0) {
			return p1.getQuantity() - p2.getQuantity();
		}	
		return result;
	}
}
