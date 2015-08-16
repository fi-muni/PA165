package cz.fi.muni.pa165.dao;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.fi.muni.pa165.enums.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Price;

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class PriceRepositoryTest  extends AbstractTransactionalTestNGSpringContextTests{
	@PersistenceContext
	public EntityManager em;
	
	@Autowired
	public PriceRepository priceRepository;
	
	@Test
	public void create(){
		Price p = new Price();
		p.setCurrency(Currency.CZK);
		p.setPriceStart(new Date());
		p.setValue(new BigDecimal("1001"));
		
		Price savedPrice = priceRepository.save(p);
		Price foundPrice = priceRepository.findOne(savedPrice.getId());
		Assert.assertEquals(savedPrice.getPriceStart(), foundPrice.getPriceStart());
	}
	
	@Test
	public void update(){
		Price p = new Price();
		p.setCurrency(Currency.CZK);
		p.setPriceStart(new Date());
		p.setValue(new BigDecimal("1001"));
		
		Price savedPrice = priceRepository.save(p);
		Price foundPrice = priceRepository.findOne(savedPrice.getId());
		Assert.assertEquals(new BigDecimal("1001"), foundPrice.getValue());

		//Change price and call save();
		foundPrice.setValue(new BigDecimal("2"));
		foundPrice = priceRepository.findOne(savedPrice.getId());
		Assert.assertEquals(new BigDecimal("2"), foundPrice.getValue());
		
	}
}
