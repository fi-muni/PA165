package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.facade.ProductFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.exceptions.ServerProblemException;
import cz.muni.fi.pa165.restapi.hateoas.ProductResource;
import cz.muni.fi.pa165.restapi.hateoas.ProductResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * SpringMVC controller for managing REST requests for the product resources. Conforms to HATEOAS principles.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@RestController
@ExposesResourceFor(ProductDTO.class)
@RequestMapping("/products")
public class ProductsRestController {

    private final static Logger log = LoggerFactory.getLogger(ProductsRestController.class);

    public ProductsRestController(@Autowired ProductFacade productFacade,@Autowired ProductResourceAssembler productResourceAssembler) {
        this.productFacade = productFacade;
        this.productResourceAssembler = productResourceAssembler;
    }

    private ProductFacade productFacade;

    private ProductResourceAssembler productResourceAssembler;


    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<ProductResource>> getProducts() {
        log.debug("rest getProducts()");
        List<ProductResource> resourceCollection = productResourceAssembler.toResources(productFacade.getAllProducts());
        Resources<ProductResource> productsResources = new Resources<>(resourceCollection,
                linkTo(ProductsRestController.class).withSelfRel(),
                linkTo(ProductsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(productsResources, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<ProductResource> getProduct(@PathVariable("id") long id) throws Exception {
        log.debug("rest getProduct({})", id);
        ProductDTO productDTO = productFacade.getProductWithId(id);
        if (productDTO == null) throw new ResourceNotFoundException("product " + id + " not found");
        ProductResource resource = productResourceAssembler.toResource(productDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
    public void productImage(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductDTO productDTO = productFacade.getProductWithId(id);
        byte[] image = productDTO.getImage();
        if (image == null) {
            response.sendRedirect(request.getContextPath() + "/no-image.png");
        } else {
            response.setContentType(productDTO.getImageMimeType());
            ServletOutputStream out = response.getOutputStream();
            out.write(image);
            out.flush();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteProduct(@PathVariable("id") long id) throws Exception {
        log.debug("rest deleteProduct({})", id);
        try {
            productFacade.deleteProduct(id);
        } catch (IllegalArgumentException ex) {
            log.error("product " + id + " not found");
            throw new ResourceNotFoundException("product " + id + " not found");
        } catch (Throwable ex) {
            log.error("cannot delete product " + id + " :" + ex.getMessage());
            Throwable rootCause=ex;
            while ((ex = ex.getCause()) != null) {
                rootCause = ex;
                log.error("caused by : " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
            throw new ServerProblemException(rootCause.getMessage());
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<ProductResource> createProduct(@RequestBody @Valid ProductCreateDTO product, BindingResult bindingResult) throws Exception {
        log.debug("rest createProduct()");
        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = productFacade.createProduct(product);
        ProductResource resource = productResourceAssembler.toResource(productFacade.getProductWithId(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
