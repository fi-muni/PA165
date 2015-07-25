package cz.fi.muni.pa165.service;

import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;

/**
 * An interface that defines a service access to the {@link Product} entity.
 */

@Service
public interface CategoryService {
	Category findById(Long id);
	void create(Category category);
	void remove(Category c);
}
