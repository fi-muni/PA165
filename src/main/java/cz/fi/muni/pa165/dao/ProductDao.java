package cz.fi.muni.pa165.dao;

import java.util.List;

import cz.fi.muni.pa165.entity.Product;

public interface ProductDao {
	public void create(Product p);
	public Product findById(Long id);
	public List<Product> findAll();
	public Product update(Product p);
}
