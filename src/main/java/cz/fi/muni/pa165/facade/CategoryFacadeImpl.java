package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dao.CategoryDao;
import cz.fi.muni.pa165.dto.CategoryDTO;

import javax.inject.Inject;
import java.util.List;

public class CategoryFacadeImpl implements CategoryFacade
{
    @Inject
    CategoryDao categoryDao;

    @Override public List<CategoryDTO> getAllCategories()
    {
        return FacadeUtils.mapTo(categoryDao.findAll(),CategoryDTO.class);
    }

    @Override public CategoryDTO getCategoryById(Long id)
    {
        return FacadeUtils.mapTo(categoryDao.findById(id),CategoryDTO.class);
    }
}
