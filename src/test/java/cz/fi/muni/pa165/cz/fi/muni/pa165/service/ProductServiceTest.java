package cz.fi.muni.pa165.cz.fi.muni.pa165.service;

import static org.mockito.BDDMockito.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.dto.PriceDTO;
import cz.fi.muni.pa165.dto.PriceDTO.Currency;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.dto.ProductDTO.Color;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.service.ProductService;
import cz.fi.muni.pa165.testutils.FieldSetAnswer;

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
public class ProductServiceTest  extends AbstractTestNGSpringContextTests 
{
    @Mock
    private ProductDao productDao;

    @Autowired
    @InjectMocks
    private ProductService productService;

    @BeforeClass
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }
    
	@Test
	public void createProductTest() {
		PriceDTO p = new PriceDTO();
		p.setCurrency(Currency.CZK);
		p.setValue(new BigDecimal("1000"));
		
		ProductDTO validProduct = new ProductDTO();
		validProduct.setColor(Color.BLACK);
		validProduct.setCurrentPrice(p);

		doAnswer( new FieldSetAnswer(0, "id", 1l)).when(productDao).create(new Product());

		productService.createProduct(validProduct);
		
		Assert.assertEquals(validProduct.getId(), new Long(1l));
	}

}