package cz.fi.muni.pa165.ws.repositories;

import cz.fi.muni.pa165.ws.entities.prices.Currency;
import cz.fi.muni.pa165.ws.entities.prices.Price;
import cz.fi.muni.pa165.ws.entities.products.Color;
import cz.fi.muni.pa165.ws.entities.products.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * This class represents resources of type Product that we will use in our
 * application. The class Product is generated automatically from the XSD schema
 * in src/main/resources
 *
 * @author brossi
 */
@Component
public class ProductRepository {

    private static final AtomicInteger counter  = new AtomicInteger(0);
    private static final List<Product> products = new ArrayList<>();


    /**
     * Initialize the repository for products we use @PostConstruct to initialize
     * after dependency injection has been performed see
     * https://docs.oracle.com/javaee/7/api/javax/annotation/PostConstruct.html
     * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/beans.html#beans-factory-nature
     */
    @PostConstruct
    public void initData() {
        
        final Price p1 = new Price();
        p1.setValue(new BigDecimal("35.99"));
        p1.setCurrency(Currency.CZK);
        
        final Price p2 = new Price();
        p2.setValue(new BigDecimal("45.99"));
        p2.setCurrency(Currency.CZK);
        
        final Price p3 = new Price();
        p3.setValue(new BigDecimal("55.99"));
        p3.setCurrency(Currency.CZK);
        
        final Price p4 = new Price();
        p4.setValue(new BigDecimal("22.50"));
        p4.setCurrency(Currency.CZK);

        final Price p5 = new Price();
        p5.setValue(new BigDecimal("14.99"));
        p5.setCurrency(Currency.CZK);
        
        final Price p6 = new Price();
        p6.setValue(new BigDecimal("33.99"));
        p6.setCurrency(Currency.CZK);
        
        products.add(createProduct("Raspberry PI", "miniPC", Color.GREEN, p1));
        products.add(createProduct("Arduino Uno", "miniPC", Color.BLUE, p2));
        products.add(createProduct("Arduino Zero", "miniPC", Color.BLUE, p3));
        products.add(createProduct("Beagle Bone", "miniPC", Color.BLUE, p4));
        products.add(createProduct("PCDuino", "miniPC", Color.BLUE, p5));
        products.add(createProduct("Galaxy", "phone", Color.BLACK, p6));

    }

    /**
     * Get a product by specifying the name
     *
     * @param name name of the product to find
     * @return
     */
    public Product getProductByName(String name) {
        Assert.notNull(name);
        return products.stream().filter(u -> u.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Get a product by specifying the id
     *
     * @param id the identifier of the product to find
     * @return
     */
    public Product getProductById(long id) {
        return products.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }
    
    /**
     * Get the list of all the products
     * 
     * @return List of products
     */
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    /**
     *
     * @param name
     * @param description
     * @param color
     * @return
     */
    private Product createProduct(String name, String description, Color color, Price price) {
        Product product = new Product();

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        XMLGregorianCalendar xmlDate = null;

        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        product.setId(counter.incrementAndGet());
        product.setName(name);
        product.setDescription(description);
        product.setColor(color);
        product.setAddedDate(xmlDate);
        product.setPrice(price);

        return product;
    }

}
