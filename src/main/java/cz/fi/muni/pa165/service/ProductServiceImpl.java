package cz.fi.muni.pa165.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.dao.PriceRepository;
import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Price.Currency;
import cz.fi.muni.pa165.entity.Product;

/**
 * Implementation of the {@link ProductService}. This class is part of the service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 */

@Service
public class ProductServiceImpl implements ProductService
{
	@Inject
    private ProductDao productDao;
    
	@Inject
    private PriceRepository priceRepository;
    
    @Override 
	public Product findById(Long id){ 
    	return productDao.findById(id);
    }
    
    @Override
    public Product createProduct(Product p) {
        productDao.create(p);
        return p;
    }

    @Override
    public void deleteProduct(Product p){
        productDao.remove(p.getId());
    }

	/**
	 * TODO implement this to convert between currencies with static currency exchange rate.
	 * Later we should explain that in reality, the service would call external service through a new DAO
	 */
	@Override
	public Price convertToCurrency(Price price, Currency currency) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * TODO implement change price that would use the convertToCurrency above
	 */
	@Override
	public void changePrice(Product p, Price newPrice) {
		BigDecimal difference = p.getCurrentPrice().getValue().subtract(newPrice.getValue());
		BigDecimal percents = difference.abs().divide(p.getCurrentPrice().getValue(),5,RoundingMode.HALF_UP); 
		if (percents.compareTo(new BigDecimal("0.1")) > 0){
			throw new  ProductServiceException("It is not allowed to change the price by more than 10%");
		}
		
		priceRepository.save(newPrice);
		p.setCurrentPrice(newPrice);
	}
	
	/**
	 * TODO implement logic that will disallow to add the same category twice
	 */
	@Override
	public void addCategory(Product product, Category category) {
		product.addCategory(category);
	}

	
	
}
