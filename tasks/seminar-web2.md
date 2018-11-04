## Seminar Web 2 Tasks - Spring MVC


**Task 01 (project build)** 

In a new folder, checkout the tag seminar-web2_step1 from https://github.com/fi-muni/PA165
and build the whole project. Then run the eshop-spring-mvc subproject. The application needs some time to start, wait for the messages "Tomcat 9.x started on port [8080], Press Ctrl-C to stop the container...".
```
mkdir seminar-web2
cd seminar-web2
git clone -b seminar-web2_step1 https://github.com/fi-muni/PA165
cd PA165/
mvn install
cd eshop-spring-mvc
mvn cargo:run
```

**Task 02 (responsive web design)** 

Visit the web application at [http://localhost:8080/eshop/](http://localhost:8080/eshop/). 

Today web applications need to display correctly on a wide variety of devices, from 3" smartphones to 30" desktop monitors, with which users interact by a mouse or a touchscreen. This provides a challenge for web design.  A solution is the so called *responsive web design* which adapts to various screen sizes. This example project uses the CSS framework [Bootstrap 3](http://getbootstrap.com/) for it. Bootstrap divides device screens into 4 categories: extra small (less then 768px wide), small (768px-992px), medium (992px-1200px) and large (>1200px). It also provides 12-column grid for positioning dependent on screen size.

On the eshop home page, click on the button *Go shopping*. Change the width of your browser screen to see all four sizes.  Notice what the top navigation menu does for extra small screen. Also notice how the product images rearrange to 1, 3, 4 and 6 columns respectively on the four screen sizes.


You can stop the Tomcat by pressing CTRL+C, then rebuild and restart the application by issuing the command 
```
mvn clean package cargo:run
```

See the examples in the [Grid](https://getbootstrap.com/docs/3.3/css/#grid) section of Bootstrap 3 documentation. Edit the page *home.jsp* to display twelve buttons labelled Button1, ..., Button12, which rearrange themselves to 1 column on extra small screen, to 2 columns on small screen, to 6 columns on medium screen and to 12 columns on large screen. (Hint: you can use the JSTL tag `<c:forEach begin="1" end="12" var="i">` or a scriptlet to generate the buttons in a loop.)

**Task 03 (example SpringMVC controller)** 

On the eshop home page, click on the button *Call ExampleController*. Look at the source code of the class ExampleController and understand what it does.

Try to edit the values in the URL in the browser address bar and see how they are received by the controller. Click on the button labeled *Cause redirect now* and see how the message is passed in as a flash attribute that exist only during the first request after the redirect. Also note how the UriBuilder replaces the variables in the URL template.

Create a new method named *bar* mapped to URL */example/bar* in the ExampleController, that takes three request parameters named *c*,*d*,*e* of types String, int, boolean respectively and a Model as its parameters. Pass the parameter values to the model. Create a JSP page named *bar.jsp* that would display the values. Restart the application and call this new method by accessing the URL [http://localhost:8080/eshop/example/bar?c=X&d=9&e=true](http://localhost:8080/eshop/example/bar?c=X&d=9&e=true).

**Task 04 (order management)**
 
Check out tag seminar-web2_step2 from git:
```
git checkout -f seminar-web2_step2 
```
New files appeared:
* filter ProtectFilter that asks for email and password when administrative pages are accessed
* controller OrderControler for managing orders
* JSP pages in src/main/webapp/WEB-INF/jsp/order
 
Rebuild and restart the application. Access the page [http://localhost:8080/eshop/order/list/all](http://localhost:8080/eshop/order/list/all) either through this link or through the menu item Administration - Orders.  Use email **`admin@eshop.com`** and password **`admin`** for authentication. (Users are defined in the subproject eshop-sample-data in the class SampleDataLoadingFacadeImpl.) Click on the various buttons. View an order in the state RECEIVED and ship it, then finish it. View another order in the state RECEIVED and cancel it. 

Inspect the source code for the controller to see how it is done.
 
**Task 05 (list of users)**
 
Taking example from the existing controllers, create a new protected page displaying list of existing users. It means:
* create a new controller UserController mapped to URL prefix */user* 
* create an instance variable inside with injected UserFacade instance
* add a new method mapped to relative URL prefix */list* that retrieves list of users, adds it to model and forwards to JSP to display it
* create a new JSP in a new folder src/main/webapp/WEB-INF/jsp/user/list.jsp that displays a table of users
  
Rebuild and run the application. Access the page [http://localhost:8080/eshop/user/list](http://localhost:8080/eshop/user/list).
 
**Task 06 (validation of input values)**
Check out tag seminar-web2_step3 from git:
```
git checkout -f seminar-web2_step3 
```
New files appeared:
* class ProductController
* JSP pages in  src/main/webapp/WEB-INF/jsp/product
* validator class ProductCreateDTOValidator
 
Rebuild and run the application. Access the page [http://localhost:8080/eshop/product/list](http://localhost:8080/eshop/product/list).
Click on the button *New product*. Submit empty form and see the error messages.
 
There are two validations going on. The first one is JSR-303 validation which is driven by annotations on the class **ProductCreateDTO**
located in the subproject eshop-api, see that class. The annotations @NotNull, @Size and @Min specify requirements for valid data.
The second validation is SpringMVC-specific validation performed in the class **ProductCreateDTOValidator** which can do any complex
checking, even involving relations among multiple properties. Try to select color BLACK and price higher than 100 to see it in action.
 
Look at the source code of the class ProductController and the JSP pages to see how the form validation is implemented.
 
**Task 07 (new category form)**
 
Create a controller and JSP pages which provide creation of new categories. 
 
Using the administration web pages, create a new category and a new product in that category.
 
**Solution**

You can see the complete solution in the branch [seminar-web2](https://github.com/fi-muni/PA165/tree/seminar-web2).
