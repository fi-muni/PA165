package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.enums.Currency;
import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Product} entity.
 */

@Service
public interface ProductService {
	Product findById(Long id);
	List<Product> findAll();
	Product createProduct(Product p);
	void addCategory(Product product, Category category);
	void removeCategory(Product product, Category category);
	void changePrice(Product product, Price newPrice);
	void deleteProduct(Product p);
	Price convertToCurrency(Price price, Currency currency);

	void changeImage(Product product, byte[] photo);
}
