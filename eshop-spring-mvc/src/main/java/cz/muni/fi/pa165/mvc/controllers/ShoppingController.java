package cz.muni.fi.pa165.mvc.controllers;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.facade.CategoryFacade;
import cz.fi.muni.pa165.facade.ProductFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides the public shopping interface.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    final static Logger log = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    private CategoryFacade categoryFacade;

    @Autowired
    private ProductFacade productFacade;

    public void setCategoryFacade(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }

    public void setProductFacade(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    /**
     * Shows all categories and products.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping("/show")
    public String list(Model model) {
        log.debug("show()");
        //get all categories
        List<CategoryDTO> allCategories = categoryFacade.getAllCategories();
        model.addAttribute("categories", allCategories);

        //for each category, get products, remember that as a map from category ids to products
        Map<Long, List<ProductDTO>> categoriesToProductsMap = new HashMap<>();
        for (CategoryDTO categoryDTO : allCategories) {
            categoriesToProductsMap.put(categoryDTO.getId(), productFacade.getProductsByCategory(categoryDTO.getName()));
        }
        model.addAttribute("cat2prods", categoriesToProductsMap);

        //forward to /shopping/show.jsp
        return "shopping/show";
    }

    /**
     * Shows a product image.
     *
     * @param id       product id
     * @param response HTTP response
     * @throws IOException
     */
    @RequestMapping("/productImage/{id}")
    public void productImage(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductDTO productDTO = productFacade.getProductWithId(id);
        byte[] image = productDTO.getImage();
        if(image==null) {
            response.sendRedirect(request.getContextPath()+"/no-image.png");
        } else {
            response.setContentType(productDTO.getImageMimeType());
            ServletOutputStream out = response.getOutputStream();
            out.write(image);
            out.flush();
        }
    }

    /**
     * Shows product detail.
     *
     * @param id    product id
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping("/product/{id}")
    public String product(@PathVariable long id, Model model) {
        log.debug("product({})", id);
        model.addAttribute("product", productFacade.getProductWithId(id));
        return "shopping/product";
    }

    /**
     * Shows category detail.
     *
     * @param id    category id
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping("/category/{id}")
    public String category(@PathVariable long id, Model model) {
        log.debug("category({})", id);
        CategoryDTO categoryDTO = categoryFacade.getCategoryById(id);
        model.addAttribute("category", categoryDTO);
        model.addAttribute("products", productFacade.getProductsByCategory(categoryDTO.getName()));
        return "shopping/category";
    }
}
