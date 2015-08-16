package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.CategoryDTO;

import java.util.List;

public interface CategoryFacade
{
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
}
