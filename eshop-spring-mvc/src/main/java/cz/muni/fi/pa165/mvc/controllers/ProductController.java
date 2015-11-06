package cz.muni.fi.pa165.mvc.controllers;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.Color;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.enums.Currency;
import cz.fi.muni.pa165.facade.CategoryFacade;
import cz.fi.muni.pa165.facade.ProductFacade;
import cz.muni.fi.pa165.mvc.forms.ProductCreateDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/**
 * SpringMVC Controller for administering products.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private final static Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private CategoryFacade categoryFacade;

    /**
     * Shows a list of products with the ability to add, delete or edit.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("products", productFacade.getAllProducts());
        return "product/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        ProductDTO product = productFacade.getProductWithId(id);
        log.debug("delete({})", id);
        try {
            productFacade.deleteProduct(id);
            redirectAttributes.addFlashAttribute("alert_success", "Product \"" + product.getName() + "\" was deleted.");
        } catch (Exception ex) {
            log.error("product "+id+" cannot be deleted (it is included in an order)");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Product \"" + product.getName() + "\" cannot be deleted.");
        }
        return "redirect:" + uriBuilder.path("/product/list").toUriString();
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        log.debug("view({})", id);
        model.addAttribute("product", productFacade.getProductWithId(id));
        return "product/view";
    }

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProduct(Model model) {
        log.debug("new()");
        model.addAttribute("productCreate", new ProductCreateDTO());
        return "product/new";
    }

    @ModelAttribute("colors")
    public Color[] colors() {
        log.debug("colors()");
        return Color.values();
    }

    @ModelAttribute("currencies")
    public Currency[] currencies() {
        log.debug("colors()");
        return Currency.values();
    }

    @ModelAttribute("categories")
    public List<CategoryDTO> categories() {
        log.debug("categories()");
        return categoryFacade.getAllCategories();
    }

    /**
     * Spring Validator added to JSR-303 Validator for this @Controller only.
     * It is useful  for custom validations that are not defined on the form bean by annotations.
     * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#validation-mvc-configuring
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof ProductCreateDTO) {
            binder.addValidators(new ProductCreateDTOValidator());
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("productCreate") ProductCreateDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(productCreate={})", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "product/new";
        }
        //create product
        Long id = productFacade.createProduct(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Product " + id + " was created");
        return "redirect:" + uriBuilder.path("/product/view/{id}").buildAndExpand(id).encode().toUriString();
    }
}
