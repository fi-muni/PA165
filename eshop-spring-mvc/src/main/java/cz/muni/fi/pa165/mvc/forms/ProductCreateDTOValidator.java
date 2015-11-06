package cz.muni.fi.pa165.mvc.forms;

import cz.fi.muni.pa165.dto.Color;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The place for validation checks. Useful for checks involving multiple properties at once.
 * This SpringMVC validation is <b>additional</b> to the JSR-303 validation specified by annotations in ProductCreateDTO.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class ProductCreateDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductCreateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductCreateDTO productCreateDTO = (ProductCreateDTO) target;
        if (productCreateDTO.getColor() == null) return;
        if (productCreateDTO.getPrice() == null) return;
        if (productCreateDTO.getColor() == Color.BLACK && productCreateDTO.getPrice().longValue() > 100)
            errors.rejectValue("price", "ProductCreateDTOValidator.expensive.black.product");
    }
}
