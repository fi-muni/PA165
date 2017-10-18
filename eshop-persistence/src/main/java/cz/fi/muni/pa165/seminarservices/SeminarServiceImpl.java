package cz.fi.muni.pa165.seminarservices;

import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Filip Nguyen on 9.10.17.
 */
@Service
@Transactional
public class SeminarServiceImpl {
    @Autowired
    private ProductDao productDao;


    public void create(Product p) {
        productDao.create(p);
    }


    public List<Product> findAll() {
        return productDao.findAll();
    }

    public Product findById(Long id) {
        return productDao.findById(id);
    }

    public void remove(Product p) throws IllegalArgumentException {
        productDao.remove(p);
    }

    public List<Product> findByName(String name) {
        return productDao.findByName(name);
    }
}
