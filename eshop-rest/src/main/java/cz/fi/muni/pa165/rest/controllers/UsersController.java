package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.rest.ApiUris;
import com.fasterxml.jackson.core.JsonProcessingException;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import java.util.Collection;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Users
 * 
 * @author brossi
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UsersController {
    
    final static Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Inject
    private UserFacade userFacade;

    /**
     * returns all users according to a Summary View
     * {@link cz.fi.muni.pa165.views.View}
     *
     * @return list of UserDTOs
     * @throws JsonProcessingException
     */

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<UserDTO> getUsers() throws JsonProcessingException {
        
        logger.debug("rest getUsers()");
        return userFacade.getAllUsers();
    }

    /**
     *
     * getting user according to id
     * 
     * @param id user identifier
     * @return UserDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO getUser(@PathVariable("id") long id) throws Exception {

        logger.debug("rest getUser({})", id);

        try {
            UserDTO userDTO = userFacade.findUserById(id);
            return userDTO;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }

    }
}
