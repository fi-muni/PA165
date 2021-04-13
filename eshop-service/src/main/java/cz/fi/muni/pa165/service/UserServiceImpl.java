package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link UserService}. This class is part of the service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    // Argon2 is the best Key Derivation Function since 2015
    private final PasswordEncoder encoder = new Argon2PasswordEncoder();

    @Override
    public void registerUser(User u, String unencryptedPassword) {
        u.setPasswordHash(encoder.encode(unencryptedPassword));
        userDao.create(u);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public boolean authenticate(User u, String password) {
        return encoder.matches(password, u.getPasswordHash());
    }

    @Override
    public boolean isAdmin(User u) {
        //must get a fresh copy from database
        return findUserById(u.getId()).isAdmin();
    }

    @Override
    public User findUserById(Long userId) {
        return userDao.findById(userId);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

}
