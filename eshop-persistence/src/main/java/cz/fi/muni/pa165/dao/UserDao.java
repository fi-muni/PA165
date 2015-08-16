package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserDao {
	 void create(User u);
	 User findById(Long id);
	 User findUserByEmail(String email);
	 List<User> findAll();
}
