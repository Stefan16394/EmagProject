package products;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class CategorieStorage {
	private static int id = 1;
	private Map<Integer, ProductCategories> categories;
	private Map<ProductCategories, Map<Integer,String>> subCategories;
	
	public CategorieStorage(){
		this.fillMap();
		this.subCategories = new HashMap<>();
		for(ProductCategories category:this.categories.values()) {
			this.subCategories.put(category, new TreeMap<>((a1,a2)->a1-a2));
		}
		
		for(PhonesTabletsLaptops sub:PhonesTabletsLaptops.values()) {
			this.subCategories.get(ProductCategories.PHONES_TABLETS_LAPTOPS).put(sub.ordinal(), sub.toString());
		}
		
		
	}
	
	private void fillMap() {
		this.categories = new TreeMap<>((a1,a2)->a1-a2);
		for(ProductCategories pc:ProductCategories.values()) {
			categories.put(id++, pc);
		}
	}

	public Map<Integer, ProductCategories> getCategories() {
		return Collections.unmodifiableMap(this.categories);
	}

	public Map<ProductCategories, Map<Integer, String>> getSubCategories() {
		return Collections.unmodifiableMap(this.subCategories);
	}

	
	
	
	
}
