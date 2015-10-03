package cz.fi.muni.pa165.facade;

import java.util.List;

import cz.fi.muni.pa165.dto.CategoryDTO;
public interface CategoryFacade
{
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
}
