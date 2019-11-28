package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.ProductDTO;
import cz.muni.fi.pa165.restapi.controllers.ProductsRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Converts ProductDTO instance into ProductResource, which is later rendered into HAL JSON format with _links.
 *
 * @author Martin Kuba makub@ics.muni.cz
 *
 */
@Component
public class ProductRepresentationModelAssembler implements RepresentationModelAssembler<ProductDTO, EntityModel<ProductDTO>> {

    private final static Logger log = LoggerFactory.getLogger(ProductRepresentationModelAssembler.class);

    @Override
    public EntityModel<ProductDTO> toModel(ProductDTO productDTO) {
        long id = productDTO.getId();
        EntityModel<ProductDTO> productResource = new EntityModel<>(productDTO);
        try {
            productResource.add(linkTo(ProductsRestController.class).slash(productDTO.getId()).withSelfRel());

            Method deleteProduct = ProductsRestController.class.getMethod("deleteProduct", long.class);
            productResource.add(linkTo(deleteProduct.getDeclaringClass(),deleteProduct, id).withRel("delete"));

            Method productImage = ProductsRestController.class.getMethod("productImage", long.class, HttpServletRequest.class, HttpServletResponse.class);
            Link imageLink = linkTo(productImage.getDeclaringClass(), productImage, id).withRel("image");
            productResource.add(imageLink);
        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return productResource;
    }
}
