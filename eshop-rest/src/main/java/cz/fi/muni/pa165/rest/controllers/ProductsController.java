package cz.fi.muni.pa165.rest.controllers;

import org.springframework.web.bind.annotation.RequestMethod;
import cz.fi.muni.pa165.facade.ProductFacade;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST Controller for Products
 *
 * TODO: Annotate the controller with @RestController and add a 
 * RequestMapping using ApiUris.ROOT_URI_PRODUCTS
 */
public class ProductsController {

    final static Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Inject
    private ProductFacade productFacade;

    /**
     * Get list of Products curl -i -X GET
     * http://localhost:8080/eshop-rest/products
     * 
     * TODO: 
     * 1. annotate the method to have as method RequestMethod.GET
     * and producing application/json
     * 2. return a list of ProductDTOs

     */
    public final void getProducts() {
    }

    /**
     *
     * Get Product by identifier id curl -i -X GET
     * http://localhost:8080/eshop-rest/products/1
     * 
     * 
     * TODO: 
     * 1. add the mapping using the resource id
     * 2. retrieve the id from the request by using pathvariable
     * 3. you can return HttpStatus.NOT_FOUND 404 if the product is not found
     * (note: at the current time there is an issue at the persistence layer
     * https://github.com/fi-muni/PA165/issues/28
     * so using the facade to get one product with a non-existing id will throw
     * a Dozer exception - you can catch the exception and re-throw it )
     * You can use the class ResourceNotFoundException 
     * 
     */
    public final void getProduct(long id) throws Exception {
    }

    /**
     * Delete one product by id curl -i -X DELETE
     * http://localhost:8080/eshop-rest/products/1
     *
     * TODO: 
     * 1. delete one product identified by id
     * 2. what about deleting the same element multiple times?
     */
    public final void deleteProduct(long id) throws Exception {
    }

    /**
     * Create a new product by POST method
     * curl -X POST -i -H "Content-Type: application/json" --data 
     * '{"name":"test","description":"test","color":"UNDEFINED","price":"200",
     * "currency":"CZK", "categoryId":"1"}' 
     * http://localhost:8080/eshop-rest/products/create
     * 
     * TODO:
     * 1. use the requestbody annotation to get access to the request body
     * 2. use the ProductCreateDTO for the creation of a new product, but return a ProductDTO
     * 
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void createProduct() throws Exception {
    }

    /**
     * Update the price for one product by PUT method curl -X PUT -i -H
     * "Content-Type: application/json" --data '{"value":"16.33","currency":"CZK"}'
     * http://localhost:8080/eshop-rest/products/4
     *
     * TODO: Update the price for one product (you need to use  NewPriceDTO)
     * take care also about the price validation at the service layer
     * 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changePrice() throws Exception {
    }

    
}
