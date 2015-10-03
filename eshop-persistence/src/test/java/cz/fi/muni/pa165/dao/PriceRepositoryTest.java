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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Price;

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class PriceRepositoryTest  extends AbstractTestNGSpringContextTests{
	@Autowired
	public PriceRepository priceRepository;
	private Price savedPrice;
	
	@BeforeMethod
	public void createPrice(){
		Price p = new Price();
		p.setCurrency(Currency.CZK);
		p.setPriceStart(new Date());
		p.setValue(new BigDecimal("1001"));
		
		savedPrice = priceRepository.save(p);
	}
	
	@Test
	public void create(){
		Price foundPrice = priceRepository.findOne(savedPrice.getId());
		Assert.assertEquals(savedPrice.getPriceStart(), foundPrice.getPriceStart());
	}
	
	@Test
	public void update(){
		savedPrice.setValue(new BigDecimal("2"));
		Price found = priceRepository.findOne(savedPrice.getId());
		Assert.assertEquals(new BigDecimal("2"), found.getValue());
		
	}
}
