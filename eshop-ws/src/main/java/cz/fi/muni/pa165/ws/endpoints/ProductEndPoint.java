package cz.fi.muni.pa165.ws.endpoints;

import cz.fi.muni.pa165.ws.entities.products.GetProductRequestByName;
import cz.fi.muni.pa165.ws.entities.products.GetProductResponse;
import cz.fi.muni.pa165.ws.entities.products.GetProductsRequest;
import cz.fi.muni.pa165.ws.entities.products.Product;
import cz.fi.muni.pa165.ws.exceptions.ProductNotFoundException;
import cz.fi.muni.pa165.ws.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import java.util.List;

/**
 * This class represents an endpoint for the Product class generated from the
 * XSD definitions.
 * 
 * @author brossi
 */
@Endpoint
public class ProductEndPoint {
 
    private static final String NAMESPACE_URI = "http://muni.fi.cz/pa165/ws/entities/products";

    @Autowired
    private ProductRepository productRepository;

    
        /**
         * 
         *  Payload root defines an handler method, by using the general base namespace uri
         *  and the specific part for this handler
         *  we mark to return the ResponsePayload and we annotate the variable for the 
         *  RequestPayload
         * 
         * See http://docs.spring.io/spring-ws/site/apidocs/org/springframework/ws/server/endpoint/annotation/PayloadRoot.html
         * 
         */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequestByName")
	@ResponsePayload
	public GetProductResponse getProduct(@RequestPayload GetProductRequestByName request) {
		
            final GetProductResponse response = new GetProductResponse();
            final Product product = productRepository.getProductByName(request.getName());
            
            if (product==null){
                throw new ProductNotFoundException(request.getName());
            }
            
            response.getProduct().add(product);
            return response;
            
	}
        
         /**
         * 
         *  Get all the products that are available 
         * 
         */
        
        @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
	@ResponsePayload
	public GetProductResponse getProducts(@RequestPayload GetProductsRequest request) {
		
            final GetProductResponse response = new GetProductResponse();
            final List<Product> products = productRepository.getProducts();
            
            for (Product product : products) {
                response.getProduct().add(product);
            }

            return response;
            
	}
    
}
