package cz.fi.muni.pa165.ws.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/**
 * The SOAP Fault that will be sent out when there is a ProductNotFoundException
 * 
 * See http://docs.spring.io/spring-ws/site/apidocs/org/springframework/ws/soap/SoapFault.html
 * 
 */
@SoapFault(faultCode = FaultCode.SERVER, faultStringOrReason = "Product not found." )
public class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String productName) {
		super("could not find product " + productName );
	}
}
