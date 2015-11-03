package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public UserDTO findUserById(Long userId) {
        User user = userService.findUserById(userId);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public void registerUser(UserDTO userDTO, String unencryptedPassword) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        userService.registerUser(userEntity, unencryptedPassword);
        userDTO.setId(userEntity.getId());
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(userService.getAllUsers(), UserDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO u) {
        return userService.authenticate(
                userService.findUserById(u.getUserId()), u.getPassword());
    }

    @Override
    public boolean isAdmin(UserDTO u) {
        return userService.isAdmin(beanMappingService.mapTo(u, User.class));
    }

}
