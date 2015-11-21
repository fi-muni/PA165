package cz.fi.muni.pa165.rest;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;

import cz.fi.muni.pa165.RootWebContext;
import cz.fi.muni.pa165.facade.ProductFacade;
import cz.fi.muni.pa165.rest.controllers.ProductsController;
import org.springframework.web.context.WebApplicationContext;


@WebAppConfiguration
@ContextConfiguration(classes = { RootWebContext.class})
public class ProductsControllerTest  extends AbstractTestNGSpringContextTests {

	@Mock
	private ProductFacade productFacade;

	@Autowired
	@InjectMocks
	private ProductsController productsController;
        
         @Autowired
        private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
        

	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(productsController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();          
    }
        
}
