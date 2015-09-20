This sample shows how to use Maven tomcat7 plugin. It is not inteded to show how your projects should look like!

The project contains 2 items: TestServlet and index.jsp.

The TestServlet is a standard servlet coded according to Servlet 3.0 specification, the index.jsp is just static content.

As you can see in the pom, only thing needed to use the plugin is to add plugin tomcat7-maven-plugin. In order to develop Servlet, I also had to add dependency on Servlet API (some jar that contains e.g. WebServlet annotation). It is important to note here that this Servlet API is provided for each Servlet container and should NOT be packaged together with the application. Thats why I set it to scope <scope>provided</scope>. By having it at this scope, the depdendecy is used only during the development and after packaging and deploying to server it uses the server's Servlet API.

To run this maven exapmle: 
 1) mvn clean install tomcat7:run
 2) access static content on: http://localhost:8080/my-webapp/
 3) access the servlet on: http://localhost:8080/my-webapp/hello

Should you have any questions regarding this sample, send me an e-mail to xnguyen@fi.muni.cz
