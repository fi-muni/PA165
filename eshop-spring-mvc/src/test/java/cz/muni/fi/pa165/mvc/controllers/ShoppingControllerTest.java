package cz.muni.fi.pa165.mvc.controllers;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.facade.CategoryFacade;
import cz.fi.muni.pa165.facade.ProductFacade;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class ShoppingControllerTest {

    @Mock
    private ProductFacade productFacade;

    @Mock
    private CategoryFacade categoryFacade;

    private MockMvc mockMvc;

    private ProductDTO productDTO;
    private CategoryDTO categoryDTO;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShoppingController shoppingController = new ShoppingController();
        shoppingController.setProductFacade(productFacade);
        shoppingController.setCategoryFacade(categoryFacade);
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingController).build();
    }


    @BeforeMethod
    public void setUp() throws Exception {
        productDTO = new ProductDTO();
        productDTO.setId(1l);
        productDTO.setName("AAA");


    }

    @Test
    public void testProduct() throws Exception {

        when(productFacade.getProductWithId(1l)).thenReturn(productDTO);
        this.mockMvc.perform(get("/shopping/product/1")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", productDTO))
                .andExpect(forwardedUrl("shopping/product"))
        ;

    }

    @Test
    public void testCategory() throws Exception {
        categoryDTO = new CategoryDTO();
        categoryDTO.setId(10l);
        categoryDTO.setName("cat1");
        List<ProductDTO> prods = Collections.singletonList(productDTO);
        when(categoryFacade.getCategoryById(categoryDTO.getId())).thenReturn(categoryDTO);
        when(productFacade.getProductsByCategory(categoryDTO.getName())).thenReturn(prods);

        this.mockMvc.perform(get("/shopping/category/10")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("category", categoryDTO))
                .andExpect(model().attribute("products", prods))
                .andExpect(forwardedUrl("shopping/category"))
        ;
    }
}