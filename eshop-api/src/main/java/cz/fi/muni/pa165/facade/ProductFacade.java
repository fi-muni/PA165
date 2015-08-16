package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.NewPriceDTO;
import cz.fi.muni.pa165.dto.ProductChangeImageDTO;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.dto.ProductDTO;

import java.util.List;

public interface ProductFacade {
	Long createProduct(ProductCreateDTO p);
	void addCategory(Long productId, Long categoryId);
	void removeCategory(Long productId, Long categoryId);
	void changePrice(Long productId, NewPriceDTO newPrice);
	void deleteProduct(Long productId);
	List<ProductDTO> getAllProducts();
	ProductDTO getProductWithId(Long id);
	void changeImage(ProductChangeImageDTO productChange);

}
