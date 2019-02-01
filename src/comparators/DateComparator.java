package comparators;

import java.util.Comparator;

import products.Product;

public class DateComparator implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {
		int result = p2.getCreationDate().compareTo(p1.getCreationDate());
		if (result == 0) {
			return p1.getQuantity() - p2.getQuantity();
		}
		return result;
	}

}
