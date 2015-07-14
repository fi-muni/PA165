package cz.fi.muni.pa165.facade;

import java.util.Collection;

import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.entity.User;

public class UserFacadeImpl implements UserFacade{

	@Override
	public User findUserById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerUser(UserDTO u, String unencryptedPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean authenticate(UserDTO u, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdmin(UserDTO u) {
		// TODO Auto-generated method stub
		return false;
	}

}
