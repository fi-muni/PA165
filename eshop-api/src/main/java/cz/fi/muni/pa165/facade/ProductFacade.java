package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.NewPriceDTO;
import cz.fi.muni.pa165.dto.ProductChangeImageDTO;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.dto.ProductDTO;

import java.util.List;

public interface ProductFacade {
	public Long createProduct(ProductCreateDTO p);
	public void addCategory(Long productId, Long categoryId);
	public void removeCategory(Long productId, Long categoryId);
	public void changePrice(NewPriceDTO newPrice);
	public void deleteProduct(Long productId);
	public List<ProductDTO> getAllProducts();
	public List<ProductDTO> getProductsByCategory(String categoryName);
	public ProductDTO getProductWithId(Long id);
	public void changeImage(ProductChangeImageDTO productChange);

}
