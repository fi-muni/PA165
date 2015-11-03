package cz.fi.muni.pa165.service.facade;

import javax.inject.Inject;

import cz.fi.muni.pa165.dto.ProductChangeImageDTO;
import cz.fi.muni.pa165.dto.ProductDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.fi.muni.pa165.dto.NewPriceDTO;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.facade.ProductFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.CategoryService;
import cz.fi.muni.pa165.service.ProductService;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductFacadeImpl implements ProductFacade {

	final static Logger log = LoggerFactory.getLogger(ProductFacadeImpl.class);

	@Inject
	private ProductService productService;

	@Inject
	private CategoryService categoryService;
	
	@Autowired
	private BeanMappingService beanMappingService;

	@Override
	public Long createProduct(ProductCreateDTO p) {
        Product mappedProduct = beanMappingService.mapTo(p, Product.class);
        //map price DTO to entity
        Price price = new Price();
        price.setValue(p.getPrice());
        price.setCurrency(p.getCurrency());
        Date now = new Date();
        price.setPriceStart(now);
        mappedProduct.setAddedDate(now);
        //set price on product entity
        mappedProduct.setCurrentPrice(price);
        mappedProduct.addHistoricalPrice(price);
        //add to category
        mappedProduct.addCategory(categoryService.findById(p.getCategoryId()));
        //save product
        Product newProduct = productService.createProduct(mappedProduct);
		return newProduct.getId();
	}

	@Override
	public void addCategory(Long productId, Long categoryId) {
		productService.addCategory(productService.findById(productId),
				categoryService.findById(categoryId));
	}

	@Override
	public void removeCategory(Long productId, Long categoryId) {
		productService.removeCategory(productService.findById(productId),
				categoryService.findById(categoryId));
	}

	@Override
	public void changePrice(NewPriceDTO newPrice) {

		productService.changePrice(productService.findById(newPrice.getProductId()),
				beanMappingService.mapTo(newPrice, Price.class));
	}

	@Override
	public void deleteProduct(Long productId) {
		productService.deleteProduct(new Product(productId));
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		return beanMappingService.mapTo(productService.findAll(), ProductDTO.class);
	}

	@Override
	public ProductDTO getProductWithId(Long id) {
		return beanMappingService.mapTo(productService.findById(id), ProductDTO.class);
	}

	@Override
	public void changeImage(ProductChangeImageDTO dto) {
		Product p = productService.findById(dto.getProductId());
		p.setImage(dto.getImage());
		p.setImageMimeType(dto.getImageMimeType());
	}

	@Override
	public List<ProductDTO> getProductsByCategory(String categoryName) {
		Category c = categoryService.findByName(categoryName);
		return beanMappingService.mapTo(c.getProducts(), ProductDTO.class);
	}

}
