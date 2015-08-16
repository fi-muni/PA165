package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.service.UserService;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

@Service
@Transactional
public class UserFacadeImpl implements UserFacade{


	@Inject
	private UserService userService;

	@Override
	public UserDTO findUserById(Long userId) {
	 	return FacadeUtils.mapTo(userService.findUserById(userId), UserDTO.class);
	}

	@Override
	public void registerUser(UserDTO u, String unencryptedPassword) {
		userService.registerUser(FacadeUtils.mapTo(u, User.class),unencryptedPassword);
	}

	@Override
	public Collection<UserDTO> getAllUsers() {
		return FacadeUtils.mapTo(userService.getAllUsers(), UserDTO.class);
	}

	@Override
	public boolean authenticate(UserAuthenticateDTO u) {
		return userService.authenticate(userService.findUserById(u.getUserId()),u.getPassword());
	}

	@Override
	public boolean isAdmin(UserDTO u) {
		return userService.isAdmin(FacadeUtils.mapTo(u, User.class));
	}



}
