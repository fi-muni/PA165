package cz.fi.muni.pa165.tasks;

import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

public class ProductAndCategory {
	private Product product;
	private Category category;
	
	public ProductAndCategory(Product product, Category category) {
		super();
		this.product = product;
		this.category = category;
	}

	public Product getProduct() {
		return product;
	}

	public Category getCategory() {
		return category;
	}
	
	
}
