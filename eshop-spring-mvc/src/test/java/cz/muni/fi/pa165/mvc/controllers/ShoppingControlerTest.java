package cz.muni.fi.pa165.mvc.controllers;

import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.facade.ProductFacade;
import cz.fi.muni.pa165.facade.UserFacade;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
//@ContextConfiguration(classes = {MySpringMvcConfig.class})
public class ShoppingControlerTest {

    private MockMvc mockMvc;

//    @Autowired
//    private WebApplicationContext wac;

    @Mock
    private ProductFacade productFacade;

    private ShoppingControler shoppingControler;


//    @BeforeClass
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        shoppingControler = new ShoppingControler();
        shoppingControler.setProductFacade(productFacade);
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingControler).build();
    }


    @Test
    public void testProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1l);
        productDTO.setName("AAA");
        when(productFacade.getProductWithId(1l)).thenReturn(productDTO);
        this.mockMvc.perform(get("/shopping/product/1")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product",productDTO))
                .andExpect(forwardedUrl("shopping/product"))
        ;

    }
}