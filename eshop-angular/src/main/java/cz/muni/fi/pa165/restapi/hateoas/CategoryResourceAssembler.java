package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.muni.fi.pa165.restapi.controllers.CategoriesRestController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Assembles a HATEOS-compliant representation of a category from a CategoryDTO.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Component
public class CategoryResourceAssembler extends ResourceAssemblerSupport<CategoryDTO, CategoryResource> {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    EntityLinks entityLinks;

    final static org.slf4j.Logger log = LoggerFactory.getLogger(CategoryResourceAssembler.class);

    public CategoryResourceAssembler() {
        super(CategoriesRestController.class, CategoryResource.class);
    }


    @Override
    public CategoryResource toResource(CategoryDTO categoryDTO) {
        long id = categoryDTO.getId();
        CategoryResource categoryResource = new CategoryResource(categoryDTO);
        try {
            Link catLink = entityLinks.linkForSingleResource(CategoryDTO.class, id).withSelfRel();
            categoryResource.add(catLink);

            Link productsLink = entityLinks.linkForSingleResource(CategoryDTO.class, id).slash("/products").withRel("products");
            categoryResource.add(productsLink);

        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return categoryResource;
    }
}
