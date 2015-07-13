package cz.fi.muni.pa165.facade;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;

import cz.fi.muni.pa165.dto.PriceDTO;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.service.ProductService;

public class ProductFacadeImpl implements ProductFacade {

	@Inject
	private ProductService productService; 
	
    @Autowired
    private DozerBeanMapper dozer;
    
	@Override
	public Long createProduct(ProductCreateDTO p) {
		Product newProduct  = productService.createProduct(dozer.map(p, Product.class));
		return newProduct.getId();
	}

	@Override
	public void addCategory(Long productId, Long categoryId) {
		productService.addCategory(new Product(productId), new Category(categoryId));
	}

	@Override
	public void changePrice(Long productId, PriceDTO newPrice) {
		productService.changePrice(new Product(productId), dozer.map(newPrice, Price.class));
	}

	@Override
	public void deleteProduct(Long productId) {
		productService.deleteProduct(new Product(productId));
	}

}
