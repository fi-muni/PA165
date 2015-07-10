package cz.fi.muni.pa165.service;

import java.util.List;

import cz.fi.muni.pa165.entity.Product;

public interface ProductService {
	void createProduct(Product p);

	void updateProduct(Product p);

	void deleteProduct(Product p);

	List<Product> getProductByName(String namePattern);
}
