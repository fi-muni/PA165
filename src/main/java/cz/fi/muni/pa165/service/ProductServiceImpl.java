package cz.fi.muni.pa165.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;

/**
 * Implementation of the {@link ProductService}. This class is part of the service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 */

@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private DozerBeanMapper doozer;

    
    public ProductDTO createProduct(ProductDTO p) {
    	Product entity = doozer.map(p, Product.class);
        productDao.create(entity);
        return doozer.map(entity, ProductDTO.class);
    }

    public void deleteProduct(ProductDTO p){
        productDao.remove(p.getId());
    }

    public ProductDTO getProductByName(String namePattern){
        return doozer.map(productDao.findByName(namePattern), ProductDTO.class);
    }

	@Override
	public ProductDTO changePrice(Long id, Price newPrice) {
		Product p = productDao.findById(id);
		p.setCurrentPrice(newPrice);
		return doozer.map(p, ProductDTO.class);
	}
    
}
