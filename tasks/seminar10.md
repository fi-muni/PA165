## Seminar 10 Tasks - Spring REST

You can see the complete solution in the branch [seminar10](https://github.com/fi-muni/PA165/tree/seminar10).

**Task 01 (project build)** 

In a new folder, checkout the tag seminar10_step1 from https://github.com/fi-muni/PA165 and build the whole project. Then run the eshop-rest subproject.

```
mkdir seminar10
cd seminar10
git clone -b seminar10_step1 https://github.com/fi-muni/PA165
cd PA165/
module add maven
mvn install
cd eshop-rest
mvn tomcat7:run
```

Check that you can interact with the REST API. You can use [curl](http://curl.haxx.se/), an open source tool for this goal that is available on your machine. From the command line:
```
curl -X GET http://localhost:8080/eshop-rest/
curl -X GET http://localhost:8080/eshop-rest/orders?status=ALL
curl -X GET http://localhost:8080/eshop-rest/products
```

Use the '-i' parameter of curl to include information about the HTTP headers.

```
curl -i -X GET http://localhost:8080/eshop-rest/products
```

Let's try to delete one element:

```
curl -i -X DELETE http://localhost:8080/eshop-rest/products/1
```

See that the product is gone:

```
curl -i -X GET http://localhost:8080/eshop-rest/products
```

Try to delete it again:

```
curl -i -X DELETE http://localhost:8080/eshop-rest/products/1
```


Let's try to create a new product:

```
curl -X POST -i -H "Content-Type: application/json" --data '{"name":"test","description":"test","color":"UNDEFINED","price":"200","currency":"CZK", "categoryId":"1"}' http://localhost:8080/eshop-rest/products/create
```

Let's update the price of an existing product  :

```
curl -i -X GET http://localhost:8080/eshop-rest/products/4
curl -X PUT -i -H "Content-Type: application/json" --data '{"value":"4500","currency":"CZK"}' http://localhost:8080/eshop-rest/products/4
```
Based on validation introduced couple of seminars ago, it is not possible to change the price over 10% of the actual price, so it should fail. The following should succeed:

```
curl -X PUT -i -H "Content-Type: application/json" --data '{"value":"16.33","currency":"CZK"}' http://localhost:8080/eshop-rest/products/4
curl -i -X GET http://localhost:8080/eshop-rest/products/4
```

Some of these responses might contain lots of useless data (for example image data), we will deal with this in another task.

**Task 02 (Write a low maturity level REST Controller)** 

Move to step2:
```
git checkout -f seminar10_step2 
```
As a second step, we will implement the simple REST controller that was used in the previous step. We follow here some lower REST maturity models (see lecture's slides), later we will create a more HATEOAS-oriented controller. In the eshop-rest module, open the class **ProductsController** in **package cz.fi.muni.pa165.rest.controllers** and follow the TODOs for implementation.

**Task 03 (Testing the REST Controller)** 

Move to step3:
```
git checkout -f seminar10_step3 
```

We are going now to test the controller that has been created. Open the class **cz.fi.muni.pa165.rest.ProductsControllerTest** in eshop-rest module test packages.
For testing, we are using [MockMvc](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html) and [jsonPath](https://github.com/jayway/JsonPath) for parsing the JSON format. Follow the TODOs in the comments of the class for the implementation of the tests.


**Task 04 (Dealing with Dates)** 

Move to step4:
```
git checkout -f seminar10_step4
```

The suggested way to deal with dates in a REST API is to use the [ISO 8601 format](https://www.cl.cam.ac.uk/~mgk25/iso-time.html) - in our current version of the API we are always using [timestamps](http://www.unixtimestamp.com/) (look what will happen on Jan 19th 2038 :)

To change the supported date format, configure the **Jackson Object Mapper** by using **setDateFormat**. You can read about Jackson configuration [here](https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring).

* disable **SerializationFeature.WRITE_DATES_AS_TIMESTAMPS**;
* use **setDateFormat** either using **SimpleDateFormat** or **com.fasterxml.jackson.databind.util.ISO8601DateFormat**;

You can rebuild the application and test that dates are now in a more human-readable format.


**Task 05 (Jackson Filtering)** 

Move to step5:
```
git checkout -f seminar10_step5
```

It does not make sense to always consider all of an object's fields during serialization/deserialization from DTOs. What we would like to achieve with this step is that when we are returning an user instance, we would not consider the password hash. We will see three alternative ways, that if you want you can apply to other DTOs/fields as well. Please note that for approaches i) and ii) you will need to work mostly in the **API Module** annotating DTOs, while for approach iii) you will only work in the **eshop-rest** module. 

a) Approach i

Add Jackson annotations as dependencies to the pom.xml file to the **API Module**.

```
     <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
        </dependency>
```

In the **API module**, annotate with **@JsonIgnore** in the **class UserDTO** the getter **getPasswordHash()**. At the same time, we might want to still serialize password hash (e.g. when a new user is created). To do this, annotate **setPasswordHash(..)** with  **@JsonProperty**. Look for details why annotating both getters and setters: [here](https://fasterxml.github.io/jackson-annotations/javadoc/2.0.2/com/fasterxml/jackson/annotation/JsonIgnore.html)

Also mark  @JsonIgnore

Rebuild and restart the application and try to access [http://localhost:8080/eshop-rest/users](http://localhost:8080/eshop-rest/users) (or with CURL). You should get no password hash displayed.

b) Approach ii

An alternative way is to use the Jackson Views, see the [support in Spring]( https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring).

Create one class in the **API module** in a **new package cz.fi.muni.pa165.views**: the class will just contain one public interface called **Summary**.
In the class **UserDTO**, annotate each field that you would like to have in the controller response with **@JsonView(View.Summary.class)**.
Annotate then each controller's method in which you want to have one view with **@JsonView(View.Summary.class)**. Rebuild the application and restart to look at the changes in responses.

c) Approach iii

Let's have a look at yet an alternative way to filter objects that will not modify the DTOs: using Jackson Mix-ins (see [http://wiki.fasterxml.com/JacksonMixInAnnotations](http://wiki.fasterxml.com/JacksonMixInAnnotations), note that some parts of the documentation might be outdated so you need to find the correct way to set the mixins in the mapper). In this case, we will not modify the DTOs but just filter entities based on properties at the REST module level. Let's filter out *"imageMimeType"* from the products.

* in the **eshop-rest** module, create a new package **cz.fi.muni.pa165.rest.mixin** with a new class **ProductDTOMixin**;
* Annotate this class with either **@JsonIgnoreProperties** class-level annotation with indication of the fields or **@JsonIgnore** for the field(s) you want to ignore;
* configure the **objectMapper** by mapping the mixin just created to the **ProductDTO** class;

There are also more ways to filter JSON responses (that we do not cover here), as using JSON Filters ( [http://wiki.fasterxml.com/JacksonFeatureJsonFilter](http://wiki.fasterxml.com/JacksonFeatureJsonFilter)).


**Task 06 (Spring HATEOAS)** 

Move to step6:
```
git checkout -f seminar10_step6 
```

We are now looking into building a real HATEOAS REST service. We will take the **Products** controller as an example for this process. What we would like to return are resources in a format that allows to "navigate" through different resources:

```
{"links":[{"rel":"self","href":"http://localhost:8080/eshop-rest/products"}],"content":[{"id":1, ... "links":[{"rel":"self","href":"http://localhost:8080/eshop-rest/products/1"}]},{"id":2,....
```

In this case, we are looking at the list of products that provides a link to self, as well each resource will also have a link to where additional operations can be performed (example, deleting the resource).

We will use Spring HATEOAS for this task (see [http://docs.spring.io/spring-hateoas/docs/current/reference/html/](http://docs.spring.io/spring-hateoas/docs/current/reference/html/).

We need first to add the Spring HATEOAS dependency in the eshop-rest module:

```
<dependency>
 <groupId>org.springframework.hateoas</groupId>
 <artifactId>spring-hateoas</artifactId>
</dependency>
```

Create a new class **ProductsControllerHateoas** in the controllers package that will mimic what done in the old **ProductsController** class. For the moment, you can just reuse the code of the previous class. Copy the code **just for three methods**: **getProducts()**, **getProduct()** and **deleteProduct()**. Remember also to **map the RestController to a different URI**, e.g. **"/products_hateoas"**.

To create links among resources, we will use **org.springframework.hateoas.Resource** to take care of mapping our DTOs (in this case only **ProductDTO** ) and managing the links. For this, we will need to map our DTO to one resource with a **resource assembler** class.
Create a new package **cz.fi.muni.pa165.rest.assemblers** and one class that will have the following signature. This will map the resource:

```
public class ProductResourceAssembler implements ResourceAssembler<ProductDTO, 
          Resource<ProductDTO>> {
          
          }
```

Implement the needed abstract method **toResource**, that will map one **ProductDTO** to a **Resource<ProductDTO>**.
Inside the method, you want to:
* Create a new **Resource<DTO>** by using in the constructor **ProductDTO**;
* add a link to the main resource **withSelfRel** so that you will add to the resource something similar to ``` "links":[{"rel":"self","href":"http://localhost:8080/eshop-rest/products_hateoas/1"}] ``` (hint: you can get link to the base URI with **linkTo(ProductsControllerHateoas.class)** );
* return the product resource;
          
After you have created the Assembler, you need now to change the controller. 

+ Your methods will return now: i) ```getProducts(): HttpEntity<Resources<Resource<ProductDTO>>>```, ii) ```getProduct(): HttpEntity<Resource<ProductDTO>> HttpEntity<Resource<ProductDTO>>```, we do not return anymore DTOs but the new resources with the mapped links.
+ You need to use the assembler to convert between DTOs and resources;
+ In **getProducts()**, you will need to add a link to self also to the overall resources, so that the output will look similar to the following:  ``` {"links":[{"rel":"self","href":"http://localhost:8080/eshop-rest/products_hateoas"}],"content":[{"id":1,"name":"Amber",...```;
                
You can add in the same way more links such as for example to the next product, or other operations that are allowed on the resource. We will add a reference to the delete operation:

+ Add to the Assembler another link, this time using **withRel("DELETE")**;
+ you can use similar **linkTo()** construct as before;


**Task 07 (Managing Exceptions)** 

Move to step7:
```
git checkout -f seminar10_step7
```

In the current version of the API, we are using **@ResponseStatus** annotated custom exceptions that are thrown by the different RestControllers. You can look at them in the **cz.fi.muni.pa165.rest.exceptions** package. Even though error messages can be added with the **reason** parameter, we would like to return a more structured error message, as well managing exceptions in a central place. With a returned error message, apart from the HTTP code, we can also return more information about the error:

```
{
    "errors": [
        "the requested resource is already existing not exist",
        "the item might have been removed by a previous call"
    ]
}
```

We will use an annotated **@ControllerAdvice** class: [https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc](https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc).

We will change the behaviour of **products/create** and the **ResourceAlreadyExistingException**. Let's try it again:

We try to create same product twice:

```
curl -X POST -i -H "Content-Type: application/json" --data '{"name":"test","description":"test","color":"UNDEFINED","price":"200","currency":"CZK", "categoryId":"1"}' http://localhost:8080/eshop-rest/products/create

curl -X POST -i -H "Content-Type: application/json" --data '{"name":"test","description":"test","color":"UNDEFINED","price":"200","currency":"CZK", "categoryId":"1"}' http://localhost:8080/eshop-rest/products/create

```

At the second call, we should get 

```
HTTP/1.1 422 Unprocessable Entity
```

* Create an **ApiError** class in package **cz.fi.muni.pa165.rest**. We will use this class to represent error messages;
* In the class, have a List of Strings with getters, setters and constructors for error messages;
* If not already done in the code, comment the line **@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="The requested resource was not found")**  in the **ResourceNotFoundException** class;
* Create a new class called **GlobalExceptionController** in **cz.fi.muni.pa165.rest.controllers** mark the class with **@ControllerAdvice**;
* add a new method **ApiError handleException(ResourceNotFoundException ex)** annotated with 
  ```
  @ExceptionHandler
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ResponseBody
  ```
* the method will return a new instance of a class that you can call **ApiError** in **cz.fi.muni.pa165.rest.controllers** that has a list of error Strings to be returned;


By trying again to create twice the same resource, the message will look now like the following:

```
HTTP/1.1 422 Unprocessable Entity
....

{"errors":["the requested resource already exists","other error information"]}
```


**Task 08 (REST Caching)** 
We can see how you can cache HTTP responses in Spring either as alternative or in combination with caching at the persistence layer (see [here](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html) if interested). For reference on HTTP caching in Spring, you can see [http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-caching](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-caching). See also [http://www.baeldung.com/2013/01/11/etags-for-rest-with-spring/](http://www.baeldung.com/2013/01/11/etags-for-rest-with-spring/) for the flow of operations and typical usage of etags.

Move to step8:
```
git checkout -f seminar10_step8
```

We will try caching with one of the GET methods of the HATEOAS class we created in one of the previous steps. Open the **ProductsControllerHateoas** class and duplicate the method **getProducts()**. Rename the new method **getProductsCached()** and add as a mapping **"/cached"** so that we can call this metod with **"/products_hateoas/cached"** for testing purposes.

Use the [documentation](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-caching) (in particular section 21.14.3).

* remember to add a **ShallowEtagHeaderFilter()** in the Initializer;
* add **org.springframework.web.context.request.WebRequest** as a parameter to your rest controller;
* create an eTag based on **productsResources.hashCode()** (you will need to enclose in quotes, "\);
* use **webRequest::checkNotModified()** to check if the eTag has been modified - see also [here](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/context/request/WebRequest.html#checkNotModified-java.lang.String-) for an example;

You can now get the etag from a request:

```
curl -X GET -i http://localhost:8080/eshop-rest/products_hateoas/cached
```

You can look at the etag fields in the header and reuse them for the next request. First try to add a new product, the etag should change.

```
curl -X POST -i -H "Content-Type: application/json" --data '{"name":"testCaching","description":"testCaching","color":"UNDEFINED","price":"200","currency":"CZK", "categoryId":"1"}' http://localhost:8080/eshop-rest/products/create

curl -X GET -i http://localhost:8080/eshop-rest/products_hateoas/cached
```

You can try to perform the HTTP request with the etag information you last retrieved (you need to get the exact code from the last response headers):

```
curl -i -X GET http://localhost:8080/eshop-rest/products_hateoas/cached  --header 'If-None-Match: "077e8fe377ab6dfa8b765b166972ba2d6"' 
```

As long as the data does not change, you should get

```
HTTP/1.1 304 Not Modified
Server: Apache-Coyote/1.1
Access-Control-Allow-Origin: *
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
ETag: "0bec7dfe9b90af07345fc1337e0ebe7e4"
```

**Task 09 (Questions)** 

```
Q1. Given what seen in Task1: as implemented, can DELETE be considered an idempotent operation?
```

```
Q2. In Spring MVC, what is the difference between the @Controller annotation seen the last week and the @RestController annotation? 
```

```
Q3. Given the way in which etags have been supported in Spring, what is the advantage in using HTTP caching:
i) you can spare resources by not running queries from the persistence layer;
ii) you can spare network bandwidth with the cached response;
iii) both of i) and ii);
iiii) none of i) and ii); 
```