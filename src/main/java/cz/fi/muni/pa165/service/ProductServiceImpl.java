package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.entity.Product;
import org.dozer.DozerBeanMapper;

import javax.inject.Inject;

/**
 * Implementation of the {@link ProductService}. This class is part of the service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 */
public class ProductServiceImpl implements ProductService
{
    @Inject
    private ProductDao productDao;
    private DozerBeanMapper dozerBeanMapper;

    public void createProduct(ProductDTO p) {
        productDao.create(dozerBeanMapper.map(p, Product.class));
    }

    public void updateProduct(ProductDTO p){
        productDao.remove(p.getId());
        productDao.create(dozerBeanMapper.map(p, Product.class));
    }

    public void deleteProduct(ProductDTO p){
        productDao.remove(p.getId());
    }

    public ProductDTO getProductByName(String namePattern){
        return dozerBeanMapper.map(productDao.findByName(namePattern), ProductDTO.class);
    }

    @Inject
    public void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }
}
