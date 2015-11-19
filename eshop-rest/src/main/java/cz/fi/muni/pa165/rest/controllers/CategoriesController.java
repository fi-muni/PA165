package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.rest.ApiUris;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.facade.CategoryFacade;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

/**
 * REST Controller for Categories
 * 
 * @author brossi
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_CATEGORIES)
public class CategoriesController {

    final static Logger logger = LoggerFactory.getLogger(CategoriesController.class);

    @Inject
    private CategoryFacade categoryFacade;

    /**
     * get all the categories
     * @return list of CategoryDTOs
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CategoryDTO> getCategories() {

        logger.debug("rest getCategories()");
        return categoryFacade.getAllCategories();
    }

    /**
     * 
     * Get one category specified by id
     * 
     * @param id identifier for the category
     * @return CategoryDTO
     * @throws Exception ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CategoryDTO getCategory(@PathVariable("id") long id) throws Exception {

        logger.debug("rest getCategory({})", id);

        try {
            CategoryDTO categoryDTO = categoryFacade.getCategoryById(id);
            return categoryDTO;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }

    }
}
