package products;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import products.ProductCategories.Computers;
import products.ProductCategories.HealthBeauty;
import products.ProductCategories.PhonesTabletsLaptops;
import products.ProductCategories.TVAudioFoto;

public class CategoryStorage {
	private static int id = 1;
	private Map<Integer, ProductCategories> categories;
	private Map<ProductCategories, Map<Integer, String>> subCategories;

	public CategoryStorage() {
		this.fillCategories();
	}

	private void fillCategories() {
		this.categories = new TreeMap<Integer, ProductCategories>((a1, a2) -> a1 - a2);
		for (ProductCategories pc : ProductCategories.values()) {
			this.categories.put(id++, pc);
		}

		this.subCategories = new HashMap<ProductCategories, Map<Integer, String>>();
		for (ProductCategories category : this.categories.values()) {
			this.subCategories.put(category, new TreeMap<Integer, String>((a1, a2) -> a1 - a2));
		}

		for (PhonesTabletsLaptops ptl : PhonesTabletsLaptops.values()) {
			this.subCategories.get(ProductCategories.PHONES_TABLETS_LAPTOPS).put(ptl.ordinal() + 1, ptl.name());
		}
		for (Computers comp : Computers.values()) {
			this.subCategories.get(ProductCategories.COMPUTERS).put(comp.ordinal() + 1, comp.name());
		}
		for (TVAudioFoto taf : TVAudioFoto.values()) {
			this.subCategories.get(ProductCategories.TV_AUDIO_FOTO).put(taf.ordinal() + 1, taf.name());
		}
		for (HealthBeauty healthAndBeauty : HealthBeauty.values()) {
			this.subCategories.get(ProductCategories.HEALTH_BEAUTY).put(healthAndBeauty.ordinal() + 1,
					healthAndBeauty.name());
		}
	}

	public Map<Integer, ProductCategories> getCategories() {
		return Collections.unmodifiableMap(this.categories);
	}

	public Map<ProductCategories, Map<Integer, String>> getSubCategories() {
		return Collections.unmodifiableMap(this.subCategories);
	}
}
