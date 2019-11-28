package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.CategoryCreateDTO;
import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.facade.CategoryFacade;
import cz.fi.muni.pa165.facade.ProductFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.CategoryRepresentationModelAssembler;
import cz.muni.fi.pa165.restapi.hateoas.ProductRepresentationModelAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * SpringMVC controller for managing REST requests for the category resources. Conforms to HATEOAS principles.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@RestController
@ExposesResourceFor(CategoryDTO.class)
@RequestMapping("/categories")
public class CategoriesRestController {

    private final static Logger log = LoggerFactory.getLogger(CategoriesRestController.class);

    public CategoriesRestController(
            @Autowired ProductFacade productFacade,
            @Autowired CategoryFacade categoryFacade,
            @Autowired CategoryRepresentationModelAssembler categoryRepresentationModelAssembler,
            @Autowired ProductRepresentationModelAssembler productRepresentationModelAssembler,
            @SuppressWarnings("SpringJavaAutowiringInspection")
            @Autowired EntityLinks entityLinks
    ) {
        this.productFacade = productFacade;
        this.categoryFacade = categoryFacade;
        this.categoryRepresentationModelAssembler = categoryRepresentationModelAssembler;
        this.productRepresentationModelAssembler = productRepresentationModelAssembler;
        this.entityLinks = entityLinks;
    }

    private ProductFacade productFacade;

    private CategoryFacade categoryFacade;

    private CategoryRepresentationModelAssembler categoryRepresentationModelAssembler;

    private ProductRepresentationModelAssembler productRepresentationModelAssembler;

    private EntityLinks entityLinks;

    /**
     * Produces list of all categories in JSON.
     *
     * @return list of categories
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<CollectionModel<EntityModel<CategoryDTO>>> categories() {
        log.debug("rest categories()");
        List<CategoryDTO> allCategories = categoryFacade.getAllCategories();
        CollectionModel<EntityModel<CategoryDTO>> categoriesCollectionModel = categoryRepresentationModelAssembler.toCollectionModel(allCategories);
        categoriesCollectionModel.add(linkTo(CategoriesRestController.class).withSelfRel());
        categoriesCollectionModel.add(linkTo(CategoriesRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(categoriesCollectionModel, HttpStatus.OK);
    }

    /**
     * Produces category detail.
     *
     * @param id category identifier
     * @return category detail
     * @throws Exception if category not found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<EntityModel<CategoryDTO>> category(@PathVariable("id") long id) throws Exception {
        log.debug("rest category({})", id);
        CategoryDTO categoryDTO = categoryFacade.getCategoryById(id);
        if (categoryDTO == null) throw new ResourceNotFoundException("category " + id + " not found");
        EntityModel<CategoryDTO> categoryModel = categoryRepresentationModelAssembler.toModel(categoryDTO);
        return new HttpEntity<>(categoryModel);
    }

    /**
     * Produces a list of products in the given category.
     *
     * @param id category identifier
     * @return list of products in the category
     */
    @RequestMapping(value = "/{id}/products", method = RequestMethod.GET)
    public HttpEntity<CollectionModel<EntityModel<ProductDTO>>> products(@PathVariable("id") long id) {
        log.debug("rest category/{}/products()", id);
        CategoryDTO categoryDTO = categoryFacade.getCategoryById(id);
        if (categoryDTO == null) throw new ResourceNotFoundException("category " + id + " not found");
        List<ProductDTO> products = productFacade.getProductsByCategory(categoryDTO.getName());
        CollectionModel<EntityModel<ProductDTO>> productsCollectionModel = productRepresentationModelAssembler.toCollectionModel(products);
        productsCollectionModel.add(entityLinks.linkForItemResource(CategoryDTO.class, id).slash("/products").withSelfRel());
        return new ResponseEntity<>(productsCollectionModel, HttpStatus.OK);
    }

    /**
     * Creates a new category.
     *
     * @param categoryCreateDTO DTO object containing category name
     * @return newly created category
     * @throws Exception if something goes wrong
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<EntityModel<CategoryDTO>> createProduct(@RequestBody @Valid CategoryCreateDTO categoryCreateDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest createCategory()");
        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = categoryFacade.createCategory(categoryCreateDTO);
        EntityModel<CategoryDTO> categoryModel = categoryRepresentationModelAssembler.toModel(categoryFacade.getCategoryById(id));
        return new ResponseEntity<>(categoryModel, HttpStatus.OK);
    }
}


