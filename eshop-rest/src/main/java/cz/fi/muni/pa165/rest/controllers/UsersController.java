package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.rest.ResourceNotFoundException;
import java.util.Collection;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UsersController {
    
    @Inject
    private UserFacade userFacade; 

    @RequestMapping( method = RequestMethod.GET, produces = "application/json")
    public final Collection<UserDTO> getUsers() {
        return userFacade.getAllUsers();   
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public final UserDTO getUser(@PathVariable("id") long id) throws Exception {
        UserDTO userDTO = userFacade.findUserById(id);                
        if (userDTO != null){
            return userDTO;
        }else{
            throw new ResourceNotFoundException();
        }
            
    }
}

