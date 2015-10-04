package cz.fi.muni.pa165.cz.fi.muni.pa165.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests
{
   
    @Autowired
    private BeanMappingService beanMappingService;
    
    private List<Category> categories = new ArrayList<Category>();
    private List<Product> products = new ArrayList<Product>();
    @BeforeMethod
    public void createOrders(){
    	Category cElectro = new Category(2l);
		cElectro.setName("Electronics");
    	Category cSmall = new Category(2l);
    	cSmall.setName("Small");
		
		
		Product pTv = new Product(3l);
		pTv.addCategory(cElectro);
		pTv.setName("Sharp TV");
		products.add(pTv);
		
		Product pRadio = new Product(3l);
		pRadio.addCategory(cElectro);
		pRadio.addCategory(cSmall);
		pRadio.setName("Radio");
		products.add(pRadio);
		
		Product pSpoon = new Product(3l);
		pSpoon.addCategory(cSmall);
		pSpoon.setName("Spoon");
		products.add(pSpoon);
		
		categories.add(cElectro);
		categories.add(cSmall);
		
    }
    
    @Test
    public void shouldMapInnerCategories(){
    	List<ProductDTO> cdtos = beanMappingService.mapTo(products, ProductDTO.class);
    	Assert.assertEquals(cdtos.get(0).getCategories().size(), 1);
    	
    }
    
}
