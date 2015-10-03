package cz.fi.muni.pa165.cz.fi.muni.pa165.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.dao.PriceRepository;
import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.enums.Currency;
import cz.fi.muni.pa165.exceptions.EshopServiceException;
import cz.fi.muni.pa165.service.ExchangeService;
import cz.fi.muni.pa165.service.ProductService;
import cz.fi.muni.pa165.service.TimeService;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;

@ContextConfiguration(classes=ServiceConfiguration.class)
public class ProductServiceTest extends AbstractTransactionalTestNGSpringContextTests
{
    @Mock
    private ProductDao productDao;
    
    @Mock
    private PriceRepository priceRepository;
    @Mock
    private TimeService timeService;
     
    @Mock
    private ExchangeService exchangeService;

    @Autowired
    @InjectMocks
    private ProductService productService;

    @BeforeClass
    public void setup() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
    }
    
    private Product testProduct;
    
    private Date fabricatedTime;
    
    @BeforeMethod
    public void prepareTestProduct(){
    	testProduct = new Product();
        Price price = new Price();
        price.setCurrency(Currency.EUR);
        price.setValue(new BigDecimal(10));
        testProduct.setCurrentPrice(price);
        
        when(exchangeService.getCurrencyRate(Currency.EUR, Currency.CZK)).thenReturn(BigDecimal.valueOf(27));
    }
    
    @BeforeMethod
    public void prepareFabricatedTime(){
    	Calendar cal = Calendar.getInstance();
    	cal.set(2015,2,2,0,0,0);
    	fabricatedTime = cal.getTime();
    	
    	when(timeService.getCurrentTime()).thenReturn(fabricatedTime);
    }
    
    @Test
    public void getPricevalueInCurrency(){
    	Product p = new Product();
        Price price = new Price();
        price.setCurrency(Currency.CZK);
        price.setValue(new BigDecimal(27));
        p.setCurrentPrice(price);
        
        when(exchangeService.getCurrencyRate(Currency.CZK, Currency.CZK)).thenReturn(BigDecimal.ONE);
        when(exchangeService.getCurrencyRate(Currency.CZK, Currency.EUR)).thenReturn(BigDecimal.valueOf(0.0370));
        
        BigDecimal value = productService.getPriceValueInCurrency(p, Currency.CZK);
        Assert.assertTrue(value.compareTo(BigDecimal.valueOf(27, 0)) == 0);
        
        value=productService.getPriceValueInCurrency(p, Currency.EUR);
        Assert.assertTrue(value.compareTo(BigDecimal.valueOf(1, 0)) == 0,value.toPlainString());
    }
    
    
    @Test(expectedExceptions=EshopServiceException.class)
    public void priceChangebyTooMuch(){
        Price newPrice = new Price();
        newPrice.setCurrency(Currency.CZK);
        newPrice.setValue(BigDecimal.valueOf(298));
        productService.changePrice(testProduct, newPrice);
    }
    
    @Test
    public void acceptablePriceChange(){
        Price newPrice = new Price();
        newPrice.setCurrency(Currency.CZK);
        newPrice.setValue(BigDecimal.valueOf(297));        
        productService.changePrice(testProduct, newPrice);
        
        verify(priceRepository).save(newPrice);
        Assert.assertEquals(testProduct.getCurrentPrice(), newPrice);
        Assert.assertEquals(testProduct.getCurrentPrice().getPriceStart(), fabricatedTime);
    }

}
