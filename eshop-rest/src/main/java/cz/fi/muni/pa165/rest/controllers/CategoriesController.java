package cz.fi.muni.pa165.rest.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.facade.CategoryFacade;
import cz.fi.muni.pa165.rest.ResourceNotFoundException;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

	@Inject
	private CategoryFacade categoryFacade;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public final List<CategoryDTO> getCategories() {
		return categoryFacade.getAllCategories();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public final CategoryDTO getCategory(@PathVariable("id") long id)
			throws Exception {
		CategoryDTO categoryDTO = categoryFacade.getCategoryById(id);
		if (categoryDTO != null) {
			return categoryDTO;
		} else {
			throw new ResourceNotFoundException();
		}

	}
}
