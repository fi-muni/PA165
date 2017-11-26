## Seminar Spring-WS Tasks

In this seminar we set-up a Spring-WS project and try the *"contract-first"* approach adopted in Spring for the development of webservices.
You can see the complete solution in the branch [seminar-ws](https://github.com/fi-muni/PA165/tree/seminar-ws).

**Task 01 (project build)** 

In a new folder, checkout the tag *seminar-ws_step1* from https://github.com/fi-muni/PA165 and build the whole project. Then run the **eshop-ws** subproject. **Note:** some of the resources of the project are generated from XSD definitions, so you might get unresolved references to Java classes until you build the project. If you do schema modifications, remember to run ```mvn clean install``` in the **eshop-ws** module.

```
mkdir seminar-ws
cd seminar-ws
git clone -b seminar-ws_step1 https://github.com/fi-muni/PA165
cd PA165/
module add maven
mvn clean install
cd eshop-ws
mvn tomcat7:run
```

After running the module, you will find the published WSDL at the address http://localhost:8080/spring-ws-seminar/products.wsdl

You can have a look also at the **WebServiceConfig** class for configuration of Spring-WS and **products.xsd** in **src/main/resources** that contains the definition of products. Note how classes in packages **cz.fi.muni.pa165.ws.entities.prices** and **cz.fi.muni.pa165.ws.entities.products** are automatically generated from the schema definitions. You can look also at **pom.xml** in the section **jaxb2-maven-plugin**.

In the comments there are several references to Spring documentation, that you can follow if you are interested in one particular part. 

First we test the available endpoint with **curl**. Save the following SOAP message in one file called **request.xml** in a directory outside from the project (you can open another shell for this).

```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
				  xmlns:ga="http://muni.fi.cz/pa165/ws/entities/products">
   <soapenv:Header/>
   <soapenv:Body>
      <ga:getProductRequestByName>
         <ga:name>Raspberry PI</ga:name>
      </ga:getProductRequestByName>
   </soapenv:Body>
</soapenv:Envelope>
```
From the directory in which you created the file, you can try to get the response from the endpoint:

```curl --header "content-type: text/xml" -d @request.xml http://localhost:8080/spring-ws-seminar/products.wsdl```

To get nicer formatted output you can pipe to *xmllint* if available:

```curl --header "content-type: text/xml" -d @request.xml http://localhost:8080/spring-ws-seminar/products.wsdl | xmllint --format -```

If all goes fine, you should see a *similar* response as the following:

```
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Header/>
  <SOAP-ENV:Body>
    <ns2:getProductResponse xmlns:ns2="http://muni.fi.cz/pa165/ws/entities/products" xmlns:ns3="http://muni.fi.cz/pa165/ws/entities/prices">
      <ns2:product>
        <ns2:id>1</ns2:id>
        <ns2:name>Raspberry PI</ns2:name>
        <ns2:description>miniPC</ns2:description>
        <ns2:addedDate>2015-11-29+01:00</ns2:addedDate>
        <ns2:color>GREEN</ns2:color>
        <ns2:price>
          <ns3:id>0</ns3:id>
          <ns3:value>35.99</ns3:value>
          <ns3:currency>CZK</ns3:currency>
        </ns2:price>
      </ns2:product>
    </ns2:getProductResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

We will go through most of the steps for the creation of this module.

**Task 02 (Contract First - Schema Creation)** 

Move to step2:
```
git checkout -f seminar-ws_step2 
```

Our goal in this step is to build a *"contract-first"* application based on a schema that resembles the domain model we used to so far in the *eshop application*. 

In this step we deal with the creation of the schema. Follow the TODOs in **products.xsd** and **ProductEndPoint** in particular we need to:

* modify the schema of products according to the TODOs. Among the TODOs, you will have to add a new request getProductRequestByName that can be used to search by name and modify the existing response getProductResponse so that it can support more products (hint: you can use ```minOccurs="0" maxOccurs="unbounded"``` in the schema definition. You can read more about XSD constraints [here](http://www.w3.org/TR/xmlschema-0/#OccurrenceConstraints)). You may also create another request getProductsRequest to be used without parameter;
* add a method to get all the products in the **ProductEndPoint** (note: the return type is **GetProductResponse** contrary to what written in the TODO. This is the new class that supports also multiple products with the change in **products.xsd**);

It is also good idea to change the logback configuration so that you can get information about the SOAP messages exchanged. You can add the level to DEBUG or TRACE in **src/main/resources/logback.xml** for ```org.springframework.ws.server.MessageTracing.sent``` and ```org.springframework.ws.server.MessageTracing.received``` and see the SOAP messages after rebuilding the **eshop-ws** project.


**Task 03 (Testing Spring-WS & Exception Management)** 

Move to step3:
```
git checkout -f seminar-ws_step3 
```

We want now to provide tests for SOAP requests and responses for the methods that have been created. In the test package **cz.fi.muni.pa165.ws.test**, open the class **ProductEndpointTest** and add one test method for a Product that does not exist.

In particular you need to:
* when a product that does not exist in the service is requested, we want to return a SOAP Fault to the requestor. Hint: for this test, you can use serverOrReceiverFault() to test for the specific fault reason. To do this, you will also need a better way to manage exceptions, by using the **@SoapFault** annotation for exceptions that will be mapped to a SOAP Fault. Example given:

```
@SoapFault(faultCode = FaultCode.SERVER, faultStringOrReason = "Product not found." )

```
Remember to throw such an annotated exception from the **ProductEndpoint**, in case no product is found.

The response in this case will be like the following:

```
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Header/>
  <SOAP-ENV:Body>
     <SOAP-ENV:Fault>
             <faultcode>SOAP-ENV:Server</faultcode>
             <faultstring xml:lang="en">Product not found.</faultstring>
     </SOAP-ENV:Fault>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

Also add one method that tests the method about getting all the products (see the TODO in **ProductEndpointTest**, there is an helper response). Run all the tests and ensure that they pass. You can also try the running application with curl if you want to.

**Task 04 (Schema Validation)** 

Move to step4:
```
git checkout -f seminar-ws_step4
```

It is a good idea to validate the schema of the requests and responses. For this, we can add an interceptor. Spring-WS gives you the possibility of adding interceptors for different purposes. We will use in this case the **PayloadValidatingInterceptor**. Open the class **WebServiceConfig** and implement the following changes:

* register a new bean of type **PayloadValidatingInterceptor**;
* you need to configure the interceptor by setting the schema to validate (returned by **productsSchema()**) and set to validate requests and responses;
* override then **addInterceptors()** and add the new interceptor;
        

You can try the following request with curl. It has an additional "description" field not present in the way we defined the request:
    
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
				  xmlns:ga="http://muni.fi.cz/pa165/ws/entities/products">
   <soapenv:Header/>
   <soapenv:Body>
      <ga:getProductRequestByName>
         <ga:description>invalid</ga:description>
         <ga:name>Raspberry PI</ga:name>
      </ga:getProductRequestByName>
   </soapenv:Body>
</soapenv:Envelope>
```

```curl --header "content-type: text/xml" -d @request.xml http://localhost:8080/spring-ws-seminar/products.wsdl```

With the validation interceptor you should get similar message as:

```
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Header/>
     <SOAP-ENV:Body>
        <SOAP-ENV:Fault>
     <faultcode>SOAP-ENV:Client</faultcode><faultstring xml:lang="en">Validation error</faultstring><detail><spring-ws:ValidationError xmlns:spring-ws="http://springframework.org/spring-ws">cvc-complex-type.2.4.a: Invalid content was found starting with element 'ga:description'. One of '{"http://muni.fi.cz/pa165/ws/entities/products":name}' is expected.</spring-ws:ValidationError></detail>
        </SOAP-ENV:Fault>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

You can rebuild the application without interceptor and you should get instead the expected response message.


You can read more about different interceptors in Spring-WS documentation: [https://docs.spring.io/spring-ws/docs/current/reference/#server-endpoint-interceptor](https://docs.spring.io/spring-ws/docs/current/reference/#server-endpoint-interceptor)

