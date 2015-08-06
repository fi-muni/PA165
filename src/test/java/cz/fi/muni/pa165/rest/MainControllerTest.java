package cz.fi.muni.pa165.rest;

import cz.fi.muni.pa165.rest.controllers.MainController;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.testng.annotations.Test;

public class MainControllerTest {

    private final MockMvc mockMvc = standaloneSetup(new MainController())
            .build();

    @Test
    public void mainControllerTest() throws Exception {

        mockMvc.perform(get("/")).andDo(print())                
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                
                .andExpect(jsonPath("orders_url").value("/orders"))
                .andExpect(jsonPath("users_url").value("/users"))
                .andExpect(jsonPath("products_url").value("/products"));
    }
}
