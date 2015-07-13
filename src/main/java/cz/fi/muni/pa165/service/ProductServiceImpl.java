package cz.fi.muni.pa165.service;

import java.math.BigDecimal;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.entity.Category;
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
    
    @Override
    public Product createProduct(Product p) {
        productDao.create(p);
        return p;
    }

    @Override
    public void deleteProduct(Product p){
        productDao.remove(p.getId());
    }

	@Override
	public void changePrice(Product p, Price newPrice) {
		p = productDao.findById(p.getId());
		
		BigDecimal difference = p.getCurrentPrice().getValue().subtract(newPrice.getValue());
		if (difference.abs().divide(p.getCurrentPrice().getValue()).compareTo(new BigDecimal("0.1")) > 0){
			throw new  ProductServiceException("It is not allowed to change the price by more than 10%");
		}
		
		p.setCurrentPrice(newPrice);
	}
	
	@Override
	public void addCategory(Product product, Category category) {
		product =  productDao.findById(product.getId());
		product.addCategory(category);
	}
}
