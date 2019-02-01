package comparators;

import java.util.Comparator;

import products.Product;

public class RateComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		// TODO Auto-generated method stub
		int result = (int) (p2.getRate() - p1.getRate());
		if (result == 0) {
			return p1.getQuantity() - p2.getQuantity();
		}
		return result;
	}

}
