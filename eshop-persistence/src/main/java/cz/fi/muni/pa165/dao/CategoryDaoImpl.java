package cz.fi.muni.pa165.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.entity.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Category findById(Long id) {
		return em.find(Category.class, id);
	}

	@Override
	public List<Category> findAll() {
		return em.createQuery("select c from Category c", Category.class)
				.getResultList();
	}

	@Override
	public void create(Category c) {
		em.persist(c);
	}

	@Override
	public void delete(Category c) {
		em.remove(c);
	}

	@Override
	public Category findByName(String name) {
		try {
			return em
					.createQuery("select c from Category c where name = :name",
							Category.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException nrf) {
			return null;
		}
	}
}
