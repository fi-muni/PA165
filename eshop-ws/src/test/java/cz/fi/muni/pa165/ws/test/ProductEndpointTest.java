package cz.fi.muni.pa165.ws.test;

import cz.fi.muni.pa165.ws.app.WebServiceConfig;
import cz.fi.muni.pa165.ws.endpoints.ProductEndPoint;
import cz.fi.muni.pa165.ws.entities.prices.Currency;
import cz.fi.muni.pa165.ws.entities.prices.Price;
import cz.fi.muni.pa165.ws.entities.products.Color;
import cz.fi.muni.pa165.ws.entities.products.Product;
import cz.fi.muni.pa165.ws.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.transform.Source;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.xml.transform.StringSource;
import org.springframework.ws.test.server.MockWebServiceClient;

import static org.mockito.Mockito.doReturn;
import static org.springframework.ws.test.server.RequestCreators.*;
import static org.springframework.ws.test.server.ResponseMatchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * Test class for the ProductEndpoint class
 * We use MockWebServiceClient to build requests for the endpoint
 * and assert the return response payload
 * 
 */
@WebAppConfiguration
@ContextConfiguration(classes = {WebServiceConfig.class}) 
public class ProductEndpointTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ProductRepository productRepository;

    @Autowired
    @InjectMocks
    private ProductEndPoint productEndPoint;

    @Autowired
    private ApplicationContext applicationContext;

    
    /**
     * See http://docs.spring.io/spring-ws/site/apidocs/org/springframework/ws/test/client/MockWebServiceServer.html
     */
    private MockWebServiceClient mockClient;

    @BeforeClass
    public void createClient() {
        MockitoAnnotations.initMocks(this);
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    
    /**
     * Testing no valid product requested. We are expecting to receive a SOAP fault with the message 
     * that is given in the ProductNotFoundException custom exception we defined for the case in which 
     * no products are found
     * 
     * @throws Exception 
     */
     @Test
    public void productEndpointGetProductRequestNoProduct() throws Exception {

        doReturn(null).when(productRepository).getProductByName("No product");
                
        Source requestPayload = new StringSource(
                "<getProductRequestByName xmlns='http://muni.fi.cz/pa165/ws/entities/products'>"
                + "<name>No product</name>"
                + "</getProductRequestByName>");

        mockClient.sendRequest(withPayload(requestPayload)).
                andExpect(serverOrReceiverFault("Product not found."));
    }
    
    /**
     * Test by passing a request based on a products's name: expected return is a
     * payload with getProductResponse format
     * @throws Exception
     */
    @Test
    public void productEndpointGetProductRequest() throws Exception {
        
        final List<Product> products = this.createProducts();
        doReturn(products.get(0)).when(productRepository).getProductByName("Raspberry PI");
                
        Source requestPayload = new StringSource(
                "<getProductRequestByName xmlns='http://muni.fi.cz/pa165/ws/entities/products'>"
                + "<name>Raspberry PI</name>"
                + "</getProductRequestByName>");

        Source responsePayload = new StringSource(
                "<ns2:getProductResponse xmlns:ns2='http://muni.fi.cz/pa165/ws/entities/products'"
                + " xmlns:ns3='http://muni.fi.cz/pa165/ws/entities/prices'>"
                + "<ns2:product><ns2:id>1</ns2:id><ns2:name>Raspberry PI</ns2:name><ns2:description>miniPC"
                + "</ns2:description><ns2:color>GREEN</ns2:color>"
                + "<ns2:price><ns3:id>0</ns3:id><ns3:value>35.99</ns3:value><ns3:currency>CZK</ns3:currency></ns2:price> "       
                + "</ns2:product></ns2:getProductResponse>");

        mockClient.sendRequest(withPayload(requestPayload)).
                andExpect(payload(responsePayload));
    }

    /**
     * Test by looking into getting all the products
     * @throws Exception
     */
    @Test
    public void productEndpointGetAllProductsRequest() throws Exception {

        doReturn(Collections.unmodifiableList(this.createProducts())).when(
				productRepository).getProducts();
        
        
        Source requestPayload = new StringSource(
                "<getProductsRequest xmlns='http://muni.fi.cz/pa165/ws/entities/products'>"
                + "</getProductsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getProductResponse xmlns:ns2='http://muni.fi.cz/pa165/ws/entities/products'"
                + " xmlns:ns3='http://muni.fi.cz/pa165/ws/entities/prices' >"        
                + "<ns2:product><ns2:id>1</ns2:id><ns2:name>Raspberry PI</ns2:name><ns2:description>miniPC</ns2:description>"
                + "<ns2:price><ns3:id>0</ns3:id><ns3:value>35.99</ns3:value><ns3:currency>CZK</ns3:currency></ns2:price> "
                + "<ns2:color>GREEN</ns2:color>"
                + "</ns2:product>"
                + "<ns2:product><ns2:id>2</ns2:id><ns2:name>Arduino Uno</ns2:name><ns2:description>miniPC</ns2:description>"
                + "<ns2:price><ns3:id>0</ns3:id><ns3:value>45.99</ns3:value><ns3:currency>CZK</ns3:currency></ns2:price> "
                + "<ns2:color>BLUE</ns2:color>"
                + "</ns2:product>"
                + "<ns2:product><ns2:id>3</ns2:id><ns2:name>Arduino Zero</ns2:name><ns2:description>miniPC</ns2:description>"
                + "<ns2:price><ns3:id>0</ns3:id><ns3:value>55.99</ns3:value><ns3:currency>CZK</ns3:currency></ns2:price> "
                + "<ns2:color>BLUE</ns2:color>"
                + "</ns2:product>"
                        
                +        "</ns2:getProductResponse>");

        mockClient.sendRequest(withPayload(requestPayload)).
                andExpect(payload(responsePayload));
    }
    
    
    
    
    /**
     * Helper method for mocking the products
     * @return list of products
     */
    private List<Product> createProducts() {

        final List<Product> products = new ArrayList<>();
        
        final Price p1 = new Price();
        p1.setValue(new BigDecimal("35.99"));
        p1.setCurrency(Currency.CZK);
        
        final Price p2 = new Price();
        p2.setValue(new BigDecimal("45.99"));
        p2.setCurrency(Currency.CZK);
        
        final Price p3 = new Price();
        p3.setValue(new BigDecimal("55.99"));
        p3.setCurrency(Currency.CZK);
        
        products.add(createProduct(1, "Raspberry PI", "miniPC", Color.GREEN, p1));
        products.add(createProduct(2, "Arduino Uno",  "miniPC", Color.BLUE, p2));
        products.add(createProduct(3, "Arduino Zero", "miniPC", Color.BLUE, p3));

        return products;
    }

    /**
     * 
     * helper method to create a new product
     * 
     * @param id
     * @param name
     * @param description
     * @param color
     * @return 
     */
    private Product createProduct(long id, String name, String description, Color color, Price price) {
        final Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setColor(color);
        product.setPrice(price);
        return product;
    }
}
