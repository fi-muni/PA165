package cz.fi.muni.pa165.service.facade;

import java.util.List;

import javax.inject.Inject;

import cz.fi.muni.pa165.dto.CategoryCreateDTO;
import cz.fi.muni.pa165.entity.Category;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.facade.CategoryFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.CategoryService;

//TODO create Facade annotation

@Service
@Transactional
public class CategoryFacadeImpl implements CategoryFacade
{
	@Autowired
    private CategoryService categoryService;

	@Autowired
    private BeanMappingService beanMappingService;
    
	 
    public List<CategoryDTO> getAllCategories()
    {
        return beanMappingService.mapTo(categoryService.findAll(),CategoryDTO.class);
    }

    @Override 
    public CategoryDTO getCategoryById(Long id)
    {
        return beanMappingService.mapTo(categoryService.findById(id),CategoryDTO.class);
    }

    @Override
    public Long createCategory(CategoryCreateDTO categoryCreateDTO) {
        Category category = new Category();
        category.setName(categoryCreateDTO.getName());
        categoryService.create(category);
        return  category.getId();
    }
}
