package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Product;

import java.util.List;

/**
 * Created by Filip Nguyen on 9.10.17.
 */
public interface ProductDao {

    void create(Product p);

    List<Product> findAll();

    Product findById(Long id);

    void remove(Product p);

    List<Product> findByName(String name) ;
}
