## Seminar JavaScript, AJAX, AngularJS

**Task 01 (project build)** 

In a new folder, checkout the tag seminar-javascript_step1 from https://github.com/fi-muni/PA165
and build the whole project. Then run the **eshop-angular** subproject. 
```
mkdir seminar-javascript
cd seminar-javascript
git clone -b seminar-javascript_step1 https://github.com/fi-muni/PA165
cd PA165/
module add maven
mvn install
cd eshop-angular
mvn tomcat7:run
```

**Task 02 (browser tools)** 

The application has a REST (Representational State Transfer) API (Application Programming Interface) available. The API conforms to the HATEOAS (Hypermedia as the Engine of Application State) principles seen in the previous seminar. Moreover, the JSON (JavaScript Object Notation) serialization of objects conforms to the **HAL (Hypertext Application Language)** format, which requires each object to have _links part linking to other resources, and collections of objects are serialized in _embedded part.
 
To see it conveniently, we will need some tools in browser. If you use Chrome, install [JSONView for Chrome](https://chrome.google.com/webstore/detail/jsonview/chklaanhfefbnpoihckbnefhakgolnmc). For Firefox up to version 56, install [JSONView for Firefox](https://jsonview.com/). For Firefox 57 and newer, visit URL **about:config** and change settings *network.http.accept.default* to value `text/html,application/xhtml+xml,application/json;q=0.9,*/*;q=0.8` which sets HTTP Accept: header to ask for JSON.

Visit the URL [http://localhost:8080/eshop/api/v1/categories/](http://localhost:8080/eshop/api/v1/categories/) which lists all eshop categories. Try following the links to see the HATEOAS principles in practise. 

Then visit the URL [http://localhost:8080/eshop/#!/shopping](http://localhost:8080/eshop/#!/shopping) which is an AngularJS application calling the REST API. 

Press the key **F12** on your keyboard. The browser should open a subwindow with developer tools. Clink on **Console** tab to see debugging logs from JavaScript.

**Task 03 (Javadoc)**

The implementation of the REST API needed quite a few  classes and packages. For such complex applications, documentation is helpful. Let's generate it. In a terminal window, go to the **eshop-angular** folder and issue the following command:
```
mvn javadoc:javadoc
```
Then open the file **target/site/apidocs/index.html** in your browser. You will see the javadoc documentation for the project.

Note, that the main description of the project was taken from the file **src/main/javadoc/overview.html**, and the description of each package was taken from the **package-info.java** file in each package in the src/main/java folder tree. Also note that this javadoc generation was enabled in the **pom.xml** file using the **maven-javadoc-plugin**, which is configured to link to other javadocs for classes from imported libraries.

There is a missing description for the **cz.muni.fi.pa165.restapi.exceptions** package. Create a **package-info.java** in that package and regenerate the javadoc.
  
 
**Task 04 (jQuery AJAX)**

Let's try an AJAX call from browser to the REST API. We will use the jQuery library for that.

Visit the URL [http://localhost:8080/eshop/jquery_example.html](http://localhost:8080/eshop/jquery_example.html) and click the button. The table will get loaded with the list of available products. See the page source to see how it is implemented.

In the same page, implement equivalent functionality for categories. Use the URL http://localhost:8080/eshop/api/v1/categories for getting the list of categories in JSON format.

**Task 05 (AngularJS basics)** 

You have already seen the URL  [http://localhost:8080/eshop/#!/shopping](http://localhost:8080/eshop/#!/shopping) in the Task 02.
It is an web interface implemented as a single-page web application using framework [AngularJS](https://angularjs.org/).

See the file **src/main/webapp/index.jsp**. It is the main HTML document. It loads three frameworks - Bootstrap, jQuery and Angular. Then it defines a navigation menu. Please note that hyperlinks in such single-page application are not links to other HTML documents, rather they use the fragment part of URL after the **#** character, followed by **!**, so for example a hyperlink to a product detail is [#!/product/25](http://localhost:8080/eshop/#!/product/25).
In this moment, only two views are implemented:
* eshop overview - [/shopping](http://localhost:8080/eshop/#!/shopping)
* product detail - e.g. [/product/1](http://localhost:8080/eshop/#!/product/1)

The main page contains very little of AngularJS functionality. It contains a DIV element with the **ng-app** attribute marking the place managed by AngularJS, and a DIV with **ng-view** attribute which is a placeholder for changing HTML views:
```
<div ng-app="pa165eshopApp">
  <div ng-view></div>
</div>
``` 
The page also loads the file **angular_app.js** which contains the JavaScript part of our AngularJS application. See the source of the file.

At the start, it initializes the whole application into a variable **pa165eshopApp** and its dependency on two modules. 

The first module is **ngRoute**, which provides the functionality of mapping URL fragments to HTML views. This module is initialized right afterwards, and for each URL fragment defines the file with HTML template and the JavaScript controller that will take care of the particular HTML view.

The seconds module is named **eshopControllers** and it implements our application. In this moment, it defines two controllers:
* **ShoppingCtrl** which makes AJAX calls for the list of categories and then for the list of products in each category, the results are stored in $scope.categories
* **ProductDetailCtrl** which makes a single AJAX call to a product detail and stores the result in $scope.product

The ShoppingCtrl is bound to the HTML template in **partials/shopping.html**, see it source. It uses the **ng-repeat** attribute to generate a list of categories and list of products in each category. The markup
```
<div ng-repeat="category in categories">
</div>
```
means that the tag together with its content will be repeated as many times as there are items in the $scope.categories variable set by the associated controller, and each item will be stored in a variable named `category`. The template then uses expressions like `{{category.name}}` to render data from variables. In this case, the variable `category` contains the JSON object that was sent by the server as part of the collection of categories. 

Similarly the ProductDetailCtrl is bound to the HTML template **partials/product_detail.html** 

**Task 06 (implement AngularJS HTML view and controller)** 
  
Each product detail page shows links to categories in which the product is contained.

Implement the HTML view for category detail.
* create a HTML template in file partials/category_detail.html
* create a controller named CategoryDetailCtrl in the angular_app.js file that loads category data from the REST API
* set up a routing for that HTML template and controller bound to URl fragment `/category/:categoryId`

All the functionality is already implemented as part of the ShoppingCtrl and its template,
so you can copy it from there.

**Task 07  (AngularJS forms)**

Check out tag seminar-javascript_step2 from git:
```
git checkout -f seminar-javascript_step2 
```
The following changes appeared:
* files partials/admin_products.html (list of products) and partials/admin_new_product.html (form for adding a new product) 
* angular_app.js now contains AdminProductsCtrl and AdminNewProductCtrl controllers
* routing for [/admin/products](http://localhost:8080/eshop/#!/admin/products) for  AdminProductsCtrl was added
* routing for [/admin/newproduct](http://localhost:8080/eshop/#!/admin/newproduct) for AdminNewProductCtrl was added 
* the index.jsp now contains Bootstrap alerts for displaying messages and angular_app.js contains definitions of functions for closing the alerts, e.g. hideSuccessAlert()
* angular_app.js now contains definition of a new directive convertToInt which handles HTML attribute convert-to-int used in admin_new_product.html, it is necessay to convert category ids from strings to integers

Please note that to keep the example simple, no authentication is implemented. 

Rebuild the application and visit the page [/admin/products](http://localhost:8080/eshop/#!/admin/products).

Delete the first product. Delete the product with id 8. See the messages. See the implementation of AdminProductsCtrl to see how the **deleteProduct** function is implemented.

Visit the page [/admin/newproduct](http://localhost:8080/eshop/#!/admin/newproduct) and create a new product. See how values in form fields are validated on the client. 

See the implementation of AdminNewProductCtrl. It prepares data for the form and stores them in the `colors`, `currencies`, `categories` and `product` variables in `$scope`. It also defines the function `create` that is called when the form submission button is clicked.
  
See the form in partials/admin_new_products.html. It uses the following AngularJS attributes: 
* the **ng-model** attribute to bind form fields to variables from $scope
* the attributes like **ng-minlength** to add validation
* the attribute **ng-show** to conditionally display error messages
* the attribute **ng-class** to mark form fields in error state with Bootstrap's `has-error` class
  
**Task 08 (implement form)**
  
Using the code described in the previous task as an example, implement pages for
* list of categories
* form for creating a new category
   
You can see the solution in the branch [seminar-javascript](https://github.com/fi-muni/PA165/tree/seminar-javascript)   



