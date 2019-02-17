package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import products.Order;
import products.Product;
import products.ProductCategories;
import storage.ProductStorage;

public class Files {
	private static final String PRODUCTS = "products";
	private static final String PRODUCT = "product";
	private static final String FILE_FORMAT = "xml.txt";
	private static final String SUBCATEGORY = "subcategory";
	private static final String CATEGORY = "category";
	private static final String PRODUCT_STORAGE = "productStorage";
	private static final String DIR_NAME = "productStatistics";
	private static final String ADMIN_JSON = "adminJson";
	private static final String USER_JSON = "userJson";

	static {
		File dir = new File(DIR_NAME);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public static void userJSON(Deque<Order> orders) {
		makeJSON(orders, USER_JSON);
	}

	public static void adminJSON(ProductStorage storage) {
		Map<ProductCategories, Map<String, Set<Product>>> products = storage.getProducts();
		makeJSON(products, ADMIN_JSON);
	}

	public static void makeJSON(Object products, String filePath) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try (PrintWriter pw = new PrintWriter(new File(DIR_NAME + File.separator + filePath))) {
			String json = gson.toJson(products);
			pw.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void makeXML(ProductStorage storage) {
		try {
			PrintWriter pw = new PrintWriter(new File(DIR_NAME + File.separator + FILE_FORMAT));
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement(PRODUCT_STORAGE);
			Map<ProductCategories, Map<String, Set<Product>>> productStorage = storage.getProducts();
			for (Entry<ProductCategories, Map<String, Set<Product>>> entry : productStorage.entrySet()) {
				Element category = root.addElement(CATEGORY);
				category.addText(entry.getKey().name());
				for (Entry<String, Set<Product>> entry2 : entry.getValue().entrySet()) {
					Element subcategory = category.addElement(SUBCATEGORY);
					subcategory.addText(entry2.getKey());
					Element products = subcategory.addElement(PRODUCTS);
					for (Product prod : entry2.getValue()) {
						Element product = products.addElement(PRODUCT);
						products.addText(prod.toString());

					}
				}
			}

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setNewLineAfterDeclaration(true);

			XMLWriter writer;

			writer = new XMLWriter(pw, format);
			writer.write(document);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
