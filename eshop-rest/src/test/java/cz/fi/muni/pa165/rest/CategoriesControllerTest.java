package cz.fi.muni.pa165.rest;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.RootWebContext;
import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.facade.CategoryFacade;
import cz.fi.muni.pa165.rest.controllers.CategoriesController;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;


@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class CategoriesControllerTest extends AbstractTestNGSpringContextTests {
    
    @Mock
    private CategoryFacade categoryFacade;

    @Autowired
    @InjectMocks
    private CategoriesController categoriesController;

    private MockMvc mockMvc;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
       mockMvc = standaloneSetup(categoriesController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    
    }

    @Test
    public void getAllCategories() throws Exception {

        doReturn(Collections.unmodifiableList(this.createCategories())).when(categoryFacade).getAllCategories();

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Electronics"))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("Home Appliances"));

    }

    @Test
    public void getValidCategory() throws Exception {

        List<CategoryDTO> categories = this.createCategories();

        doReturn(categories.get(0)).when(categoryFacade).getCategoryById(1l);
        doReturn(categories.get(1)).when(categoryFacade).getCategoryById(2l);

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("Electronics"));

        mockMvc.perform(get("/categories/2"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("Home Appliances"));

    }

    @Test
    public void getInvalidCategory() throws Exception {
        doThrow(new RuntimeException("")).when(categoryFacade).getCategoryById(1l);

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().is4xxClientError());

    }

    private List<CategoryDTO> createCategories() {
        CategoryDTO catOne = new CategoryDTO();
        catOne.setId(1l);
        catOne.setName("Electronics");

        CategoryDTO catTwo = new CategoryDTO();
        catTwo.setId(2l);
        catTwo.setName("Home Appliances");

        return Arrays.asList(catOne, catTwo);
    }
}
