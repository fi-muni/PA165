package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.NewPriceDTO;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;

public interface ProductFacade {
	Long createProduct(ProductCreateDTO p);
	void addCategory(Long productId, Long categoryId);
	void changePrice(Long productId, NewPriceDTO newPrice);
	void deleteProduct(Long productId);
}
