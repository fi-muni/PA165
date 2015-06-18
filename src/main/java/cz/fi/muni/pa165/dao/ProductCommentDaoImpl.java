package cz.fi.muni.pa165.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.entity.ProductComment;

@Repository
public class ProductCommentDaoImpl implements ProductCommentDao {

	@PersistenceContext 
	private EntityManager em;
	
	@Override
	public void create(ProductComment comment) {
		em.persist(comment);
	}

}
