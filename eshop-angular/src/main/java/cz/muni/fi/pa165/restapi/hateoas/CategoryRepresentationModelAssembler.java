package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.CategoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Assembles a HATEOS-compliant representation of a category from a CategoryDTO.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Component
public class CategoryRepresentationModelAssembler implements RepresentationModelAssembler<CategoryDTO, EntityModel<CategoryDTO>> {


    private EntityLinks entityLinks;

    private final static Logger log = LoggerFactory.getLogger(CategoryRepresentationModelAssembler.class);

    public CategoryRepresentationModelAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
                                     @Autowired EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @Override
    public EntityModel<CategoryDTO> toModel(CategoryDTO categoryDTO) {
        long id = categoryDTO.getId();
        EntityModel<CategoryDTO> categoryResource = new EntityModel<>(categoryDTO);
        try {
            Link catLink = entityLinks.linkForItemResource(CategoryDTO.class, id).withSelfRel();
            categoryResource.add(catLink);

            Link productsLink = entityLinks.linkForItemResource(CategoryDTO.class, id).slash("/products").withRel("products");
            categoryResource.add(productsLink);

        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return categoryResource;
    }
}
