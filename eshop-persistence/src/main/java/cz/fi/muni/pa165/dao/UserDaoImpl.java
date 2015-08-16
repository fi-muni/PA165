package cz.fi.muni.pa165.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager em;
		
	@Override
	public void create(User u) {
		em.persist(u);
	}

	@Override
	public User findUserByEmail(String email) {
		if (email == null || email.isEmpty())
			throw new IllegalArgumentException("Cannot search for null e-mail");			
			
		return 
				em.createQuery("select u from User u where email=:email", User.class)
				.setParameter("email", email).getSingleResult();
	}

	@Override
	public User findById(Long id) {
		return em.find(User.class, id);
	}

	@Override
	public List<User> findAll() {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
		return (List<User>) query.getResultList();
	}


}
