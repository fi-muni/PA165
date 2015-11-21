package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.rest.ApiUris;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.NewPriceDTO;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.exceptions.EshopServiceException;
import cz.fi.muni.pa165.facade.ProductFacade;
import cz.fi.muni.pa165.rest.exceptions.InvalidParameterException;
import cz.fi.muni.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

/**
 * REST Controller for Products
 *
 * @author brossi
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_PRODUCTS)
public class ProductsController {

    final static Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Inject
    private ProductFacade productFacade;

    /**
     * Get list of Products curl -i -X GET
     * http://localhost:8080/eshop-rest/products
     *
     * @return ProductDTO
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ProductDTO> getProducts() {
        logger.debug("rest getProducts()");
        return productFacade.getAllProducts();
    }

    /**
     *
     * Get Product by identifier id curl -i -X GET
     * http://localhost:8080/eshop-rest/products/1
     *
     * @param id identifier for a product
     * @return ProductDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ProductDTO getProduct(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getProduct({})", id);

        try {
            ProductDTO productDTO = productFacade.getProductWithId(id);
            return productDTO;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }

    }

    /**
     * Delete one product by id curl -i -X DELETE
     * http://localhost:8080/eshop-rest/products/1
     *
     * @param id identifier for product
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteProduct(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteProduct({})", id);
        try {
            productFacade.deleteProduct(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Create a new product by POST method
     * curl -X POST -i -H "Content-Type: application/json" --data 
     * '{"name":"test","description":"test","color":"UNDEFINED","price":"200",
     * "currency":"CZK", "categoryId":"1"}' 
     * http://localhost:8080/eshop-rest/products/create
     * 
     * @param product ProductCreateDTO with required fields for creation
     * @return the created product ProductDTO
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ProductDTO createProduct(@RequestBody ProductCreateDTO product) throws Exception {

        logger.debug("rest createProduct()");

        try {
            Long id = productFacade.createProduct(product);
            return productFacade.getProductWithId(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     * Update the price for one product by PUT method curl -X PUT -i -H
     * "Content-Type: application/json" --data '{"value":"16.33","currency":"CZK"}'
     * http://localhost:8080/eshop-rest/products/4
     *
     * @param id identified of the product to be updated
     * @param newPrice required fields as specified in NewPriceDTO
     * @return the updated product ProductDTO
     * @throws InvalidParameterException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ProductDTO changePrice(@PathVariable("id") long id, @RequestBody NewPriceDTO newPrice) throws Exception {

        logger.debug("rest changePrice({})", id);

        try {
            newPrice.setProductId(id);
            productFacade.changePrice(newPrice);
            return productFacade.getProductWithId(id);
        } catch (EshopServiceException esse) {
            throw new InvalidParameterException();
        }

    }

    /**
     * Add a new category by POST Method
     *
     * @param id the identifier of the Product to have the Category added
     * @param category the category to be added
     * @return the updated product as defined by ProductDTO
     * @throws InvalidParameterException
     */
    @RequestMapping(value = "/{id}/categories", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ProductDTO addCategory(@PathVariable("id") long id, @RequestBody CategoryDTO category) throws Exception {

        logger.debug("rest addCategory({})", id);

        try {
            productFacade.addCategory(id, category.getId());
            return productFacade.getProductWithId(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }
}