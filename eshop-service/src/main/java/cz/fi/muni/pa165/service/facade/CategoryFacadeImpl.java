package cz.fi.muni.pa165.service.facade;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.facade.CategoryFacade;
import cz.fi.muni.pa165.service.CategoryService;

//TODO create Facade annotation

@Service
@Transactional
public class CategoryFacadeImpl implements CategoryFacade
{
    @Inject
    private CategoryService categoryService;

    @Override 
    public List<CategoryDTO> getAllCategories()
    {
        return FacadeUtils.mapTo(categoryService.findAll(),CategoryDTO.class);
    }

    @Override 
    public CategoryDTO getCategoryById(Long id)
    {
        return FacadeUtils.mapTo(categoryService.findById(id),CategoryDTO.class);
    }
}
