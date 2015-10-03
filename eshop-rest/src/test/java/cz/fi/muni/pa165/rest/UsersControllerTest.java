package cz.fi.muni.pa165.rest;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.SpringMVCConfig;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.utils.UsersController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.doReturn;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@WebAppConfiguration
@ContextConfiguration(classes = {PersistenceSampleApplicationContext.class, SpringMVCConfig.class})
public class UsersControllerTest {

    @Mock
    private UserFacade userFacade;

    @Autowired
    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(usersController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void getAllUsers() throws Exception {

        doReturn(Collections.unmodifiableList(this.createUsers())).when(userFacade).getAllUsers();

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].surname").value("Smith"))
                .andExpect(jsonPath("$.[?(@.id==2)].surname").value("Williams"));

    }

    @Test
    public void getValidUser() throws Exception {

        List<UserDTO> users = this.createUsers();

        doReturn(users.get(0)).when(userFacade).findUserById(1l);
        doReturn(users.get(1)).when(userFacade).findUserById(2l);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.surname").value("Smith"));

        mockMvc.perform(get("/users/2"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.surname").value("Williams"));

    }

    @Test
    public void getInvalidUser() throws Exception {
        doReturn(null).when(userFacade).findUserById(1l);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().is4xxClientError());

    }

    private List<UserDTO> createUsers() {
        UserDTO userOne = new UserDTO();
        userOne.setId(1l);
        userOne.setGivenName("John");
        userOne.setSurname("Smith");
        
        UserDTO userTwo = new UserDTO();
        userTwo.setId(2l);
        userTwo.setGivenName("Mary");
        userTwo.setSurname("Williams");
        
        return Arrays.asList(userOne, userTwo);
    }
}