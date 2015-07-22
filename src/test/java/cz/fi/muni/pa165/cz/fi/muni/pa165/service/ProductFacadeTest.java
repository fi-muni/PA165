package cz.fi.muni.pa165.cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.PriceRepository;
import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.dto.NewPriceDTO;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.enums.Currency;
import cz.fi.muni.pa165.facade.ProductFacade;
import cz.fi.muni.pa165.service.ProductService;
import cz.fi.muni.pa165.service.ProductServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.doReturn;

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
public class ProductFacadeTest  extends AbstractTestNGSpringContextTests 
{
	@Mock
    private PriceRepository priceRepository;
	
    @Mock
    private ProductDao productDao;

    @Autowired
    @InjectMocks
    private ProductService productService;
    
    @Autowired
    private ProductFacade productFacade;

    @BeforeClass
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * TODO implement this test to test changes of price between different currencies
     */
    @Test
    public void changePriceCurrencyTest(){
    	
    }
    
	@Test(expectedExceptions=ProductServiceException.class)
	public void changePriceByMoreThanTenPercent(){
		Product product = new Product();
		Price currentPrice = new Price();
		currentPrice.setCurrency(Currency.CZK);
		currentPrice.setValue(new BigDecimal("999"));
		product.setCurrentPrice(currentPrice);
		
		doReturn(product).when(productDao).findById(3l);
		
		NewPriceDTO priceDto = new NewPriceDTO();
		priceDto.setCurrency(Currency.CZK);
		priceDto.setValue(new BigDecimal("1100"));
		productFacade.changePrice(3l, priceDto);
	}
	
	@Test()
	public void changePriceTest(){
		Product product = new Product();
		Price currentPrice = new Price();
		currentPrice.setCurrency(Currency.CZK);
		currentPrice.setValue(new BigDecimal("1001"));
		product.setCurrentPrice(currentPrice);
		
		doReturn(product).when(productDao).findById(3l);
		
		NewPriceDTO priceDto = new NewPriceDTO();
		priceDto.setCurrency(Currency.CZK);
		priceDto.setValue(new BigDecimal("1100"));
		productFacade.changePrice(3l, priceDto);
	}
	
}