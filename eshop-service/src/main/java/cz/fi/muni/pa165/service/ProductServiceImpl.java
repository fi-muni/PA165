package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.PriceRepository;
import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.enums.Currency;
import cz.fi.muni.pa165.exceptions.EshopServiceException;
import cz.fi.muni.pa165.utils.CurrencyRateUtils;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of the {@link ProductService}. This class is part of the
 * service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 */

@Service
public class ProductServiceImpl implements ProductService {

	@Inject
	private ProductDao productDao;

	@Inject
	private PriceRepository priceRepository;

	@Inject
	private TimeService timeService;
	
	@Inject
	private ExchangeService exchangeService;
	
	
	@Override
	public Product findById(Long id) {
		return productDao.findById(id);
	}

	@Override
	public List<Product> findAll() {
		return productDao.findAll();
	}

	@Override
	public Product createProduct(Product p) {
		productDao.create(p);
		return p;
	}

	@Override
	public void deleteProduct(Product p) {
		productDao.remove(p);
	}



	@Override
	public BigDecimal getPriceValueInCurrency(Product p, Currency currency) {
		BigDecimal convertRate = exchangeService.getCurrencyRate(
				p.getCurrentPrice().getCurrency(), currency);

		BigDecimal convertedValue = p.getCurrentPrice().getValue().multiply(convertRate)
				.setScale(2, RoundingMode.HALF_UP);
		
		return convertedValue;
	}

	
	@Override
	public void changePrice(Product p, Price newPrice) {
		BigDecimal oldPriceInNewCurrency = getPriceValueInCurrency(p, newPrice.getCurrency());

		BigDecimal difference = oldPriceInNewCurrency
				.subtract(newPrice.getValue());
		BigDecimal percents = difference.abs().divide(
				oldPriceInNewCurrency, 5, RoundingMode.HALF_UP);
		if (percents.compareTo(new BigDecimal("0.1")) > 0) {
			throw new EshopServiceException(
					"It is not allowed to change the price by more than 10%");
		}
		newPrice.setPriceStart(timeService.getCurrentTime());
		priceRepository.save(newPrice);
		p.addHistoricalPrice(p.getCurrentPrice());
		p.setCurrentPrice(newPrice);
	}

	@Override
	public void addCategory(Product product, Category category) {
		if (product.getCategories().contains(category)) {
			throw new EshopServiceException(
					"Product already contais this category. Product: "
							+ product.getId() + ", category: "
							+ category.getId());
		}
		product.addCategory(category);
	}

	@Override
	public void removeCategory(Product product, Category category) {
		product.removeCategory(category);
	}

}
