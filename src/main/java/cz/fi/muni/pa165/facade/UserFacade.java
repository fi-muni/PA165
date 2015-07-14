package cz.fi.muni.pa165.facade;

import java.util.Collection;

import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.entity.User;

public interface UserFacade {
	
	User findUserById(Long userId);
	
	/**
	 * Register the given user with the given unencrypted password.
	 */
	void registerUser(UserDTO u, String unencryptedPassword);

	/**
	 * Get all registered users
	 */
	Collection<UserDTO> getAllUsers();

	/**
	 * Try to authenticate a user. Return true only if the hashed password matches the records.
	 */
	boolean authenticate(UserDTO u, String password);

	/**
	 * Check if the given user is admin.
	 */
	boolean isAdmin(UserDTO u);

}
