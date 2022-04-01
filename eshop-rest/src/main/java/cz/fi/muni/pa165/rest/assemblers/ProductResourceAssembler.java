package cz.fi.muni.pa165.rest.assemblers;

import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.rest.controllers.ProductsControllerHateoas;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * This class shows a resource assembler for a HATEOAS REST Service we are
 * mapping the DTO to a resource that can provide links to the different parts
 * of the API See
 * http://docs.spring.io/spring-hateoas/docs/current/reference/html/
 *
 * @author brossi
 */
@Component
public class ProductResourceAssembler implements RepresentationModelAssembler<ProductDTO, EntityModel<ProductDTO>> {

    @Override
    public EntityModel<ProductDTO> toModel(ProductDTO productDTO) {
        EntityModel<ProductDTO> productResource = EntityModel.of(productDTO);

        try {
            productResource.add(linkTo(ProductsControllerHateoas.class).slash(productDTO.getId()).withSelfRel());
            productResource.add(linkTo(ProductsControllerHateoas.class).slash(productDTO.getId()).withRel("DELETE"));

        } catch (Exception ex) {
            Logger.getLogger(ProductResourceAssembler.class.getName()).log(Level.SEVERE, "could not link resource from ProductsControllerHateoas", ex);
        }

        return productResource;
    }
}
