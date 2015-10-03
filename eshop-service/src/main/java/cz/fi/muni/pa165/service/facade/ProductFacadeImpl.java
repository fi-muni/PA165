package cz.fi.muni.pa165.service.facade;

import javax.inject.Inject;

import cz.fi.muni.pa165.dto.ProductChangeImageDTO;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.entity.User;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.fi.muni.pa165.dao.PriceRepository;
import cz.fi.muni.pa165.dto.NewPriceDTO;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.facade.ProductFacade;
import cz.fi.muni.pa165.service.CategoryService;
import cz.fi.muni.pa165.service.ProductService;

import java.util.List;

@Service
@Transactional
public class ProductFacadeImpl implements ProductFacade {

	@Inject
	private ProductService productService; 
	
	@Inject
	private CategoryService categoryService; 

	@Override
	public Long createProduct(ProductCreateDTO p) {
		Product newProduct  = productService.createProduct(FacadeUtils.mapTo(p, Product.class));
		return newProduct.getId();
	}

	@Override
	public void addCategory(Long productId, Long categoryId) {
		productService.addCategory(productService.findById(productId), categoryService.findById(categoryId));
	}

	@Override public void removeCategory(Long productId, Long categoryId)
	{
		productService.removeCategory(productService.findById(productId), categoryService.findById(categoryId));
	}

	@Override
	public void changePrice(Long productId, NewPriceDTO newPrice) {
		
		productService.changePrice(productService.findById(productId), FacadeUtils.mapTo(newPrice, Price.class));
	}

	@Override
	public void deleteProduct(Long productId) {
		productService.deleteProduct(new Product(productId));
	}

	@Override public List<ProductDTO> getAllProducts()
	{
		return FacadeUtils.mapTo(productService.findAll(), ProductDTO.class);
	}

	@Override public ProductDTO getProductWithId(Long id)
	{
		return FacadeUtils.mapTo(productService.findById(id), ProductDTO.class);
	}

	@Override public void changeImage(ProductChangeImageDTO dto)
	{
		productService.changeImage(productService.findById(dto.getProductId()), dto.getImage());
	}

}
