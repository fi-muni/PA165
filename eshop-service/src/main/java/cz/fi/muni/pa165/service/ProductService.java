package cz.fi.muni.pa165.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.enums.Currency;


@Service
public interface ProductService {
	public Product findById(Long id);
	public List<Product> findAll();
	public Product createProduct(Product p);
	public void addCategory(Product product, Category category);
	public void removeCategory(Product product, Category category);
	public void changePrice(Product product, Price newPrice);
	public void deleteProduct(Product p);
	public BigDecimal getPriceValueInCurrency(Product p, Currency currency);
	public void changeImage(Product product, byte[] photo);
}
