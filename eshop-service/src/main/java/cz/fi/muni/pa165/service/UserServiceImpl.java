package cz.fi.muni.pa165.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.User;

/**
 * Implementation of the {@link UserService}. This class is part of the service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 */
@Service
public class UserServiceImpl implements UserService
{
	@Autowired
    private UserDao userDao;
	
    @Override
    public void registerUser(User u, String unencryptedPassword)
    {
        u.setPasswordHash(makeSHA1Hash(unencryptedPassword));
        userDao.create(u);
    }

    @Override
    public List<User> getAllUsers()
    {
        return userDao.findAll();
    }

    @Override
    public boolean authenticate(User u, String password)
    {
        return u.getPasswordHash().equals(makeSHA1Hash(password));
    }

    @Override
    public boolean isAdmin(User u)
    {
        return u.getGivenName().equals("admin");
    }

    public String makeSHA1Hash(String input)
    {
        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = input.getBytes();
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++)
            {
                hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
            }
            return hexStr;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public User findUserById(Long userId) {
		return userDao.findById(userId);
	}
}
