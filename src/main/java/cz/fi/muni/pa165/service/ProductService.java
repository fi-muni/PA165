package cz.fi.muni.pa165.service;

import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;

/**
 * An interface that defines a service access to the {@link Product} entity.
 */

@Service
public interface ProductService {
	/**
	 * Create a new {@link Product}
	 * @param p
	 */
	ProductDTO createProduct(ProductDTO p);

	/**
	 * Delete a mirror of the given ProductDTO from the database.
	 * @param p
	 */
	void deleteProduct(ProductDTO p);

	/**
	 * Get a single product with the given name
	 */
	ProductDTO getProductByName(String namePattern);
	
	ProductDTO changePrice(Long id, Price newPrice);
}
