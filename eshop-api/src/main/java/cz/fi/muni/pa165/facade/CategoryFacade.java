package cz.fi.muni.pa165.facade;

import java.util.List;

import cz.fi.muni.pa165.dto.CategoryCreateDTO;
import cz.fi.muni.pa165.dto.CategoryDTO;
public interface CategoryFacade
{
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);

    Long createCategory(CategoryCreateDTO categoryCreateDTO);
}
