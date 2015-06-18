package cz.fi.muni.pa165.dao;

import java.util.List;

import cz.fi.muni.pa165.entity.Category;


public interface CategoryDao {
	public Category findById(Long id);
	public void create(Category c);
	public void delete(Category c);
	public List<Category> findAll();
}
