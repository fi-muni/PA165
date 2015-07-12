package cz.fi.muni.pa165.service;

import java.util.List;

import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.Product;

/**
 * An interface that defines a service access to the {@link Product} entity.
 */
public interface ProductService {
	/**
	 * Create a new {@link Product}
	 * @param p
	 */
	void createProduct(ProductDTO p);

	/**
	 * Update the product with the given id with values of the ProductDTO. The persisted Product should be mirroring the ProductDTO fields.
	 * @param p
	 */
	void updateProduct(Long id, ProductDTO p);

	/**
	 * Delete a mirror of the given ProductDTO from the database.
	 * @param p
	 */
	void deleteProduct(ProductDTO p);

	/**
	 * Get a single product with the given name
	 */
	ProductDTO getProductByName(String namePattern);
}
