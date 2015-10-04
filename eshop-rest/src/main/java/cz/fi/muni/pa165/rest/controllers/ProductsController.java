package cz.fi.muni.pa165.rest.controllers;

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
import cz.fi.muni.pa165.facade.ProductFacade;
import cz.fi.muni.pa165.rest.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {
    
    @Inject
    private ProductFacade productFacade; 

    @RequestMapping( method = RequestMethod.GET, produces = "application/json")
    public final List<ProductDTO> getProducts() {
        return productFacade.getAllProducts();   
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public final ProductDTO getProduct(@PathVariable("id") long id) throws Exception {
        ProductDTO productDTO = productFacade.getProductWithId(id);                
        if (productDTO != null){
            return productDTO;
        }else{
            throw new ResourceNotFoundException();
        }
            
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public final void deleteProduct(@PathVariable("id") long id) throws Exception {
       productFacade.deleteProduct(id);            
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes = "application/json") 
    
    public final void createProduct(@RequestBody ProductCreateDTO product) throws Exception {
       productFacade.createProduct(product);            
    }
        
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public final void changePrice(@RequestBody NewPriceDTO newPrice) throws Exception {
       productFacade.changePrice(newPrice);            
    }
    
    @RequestMapping(value = "/{id}/categories", method = RequestMethod.POST, consumes = "application/json")
    public final ProductDTO addCategory(@PathVariable("id") long id, @RequestBody CategoryDTO category) throws Exception {
       productFacade.addCategory(id, category.getId());     
       return productFacade.getProductWithId(id);
    }
}
