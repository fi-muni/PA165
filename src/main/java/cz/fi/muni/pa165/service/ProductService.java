package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.enums.Currency;
import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;

/**
 * An interface that defines a service access to the {@link Product} entity.
 */

@Service
public interface ProductService {
	Product findById(Long id);
	Product createProduct(Product p);
	void addCategory(Product product, Category category);
	void changePrice(Product product, Price newPrice);
	void deleteProduct(Product p);
	Price convertToCurrency(Price price, Currency currency);
}
