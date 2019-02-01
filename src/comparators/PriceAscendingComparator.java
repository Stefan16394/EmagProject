package comparators;

import java.util.Comparator;

import products.Product;

public class PriceAscendingComparator implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {
		int result = (int) (p1.getPrice() - p2.getPrice());
		if (result == 0) {
			return p1.getQuantity() - p2.getQuantity();
		}
		return result;
	}

}
