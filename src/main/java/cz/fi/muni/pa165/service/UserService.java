package cz.fi.muni.pa165.service;

import java.util.List;

import cz.fi.muni.pa165.entity.User;

public interface UserService {
	List<User> getAllUsers();
	boolean authenticate(User u, String password);
	boolean isAdmin(User u);
}
