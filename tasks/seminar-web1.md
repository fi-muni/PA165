## Seminar Web 1 Tasks

In this seminar, we will use several steps checked out from the GitHub repository.
Each step is marked by a git tag. A git tag is a pointer to a specific commit, 
while a git branch is a pointer to the last commit in a series of commits. 
In each step, we will reset the content of the working directory to the state
marked by each tag. You can see the final solution in the branch [seminar-web1](https://github.com/fi-muni/PA165/tree/seminar-web1/webapp-plain).


**Task 01** In a new folder, checkout the tag seminar-web1_step1 from https://github.com/fi-muni/PA165. 
Open the project webapp-plain. Run the application and view it in your browser at [http://localhost:8080/webapp-plain/](http://localhost:8080/webapp-plain/).
```
mkdir seminar-web1
cd seminar-web1
git clone -b seminar-web1_step1 https://github.com/fi-muni/PA165
cd PA165/webapp-plain
module add maven
mvn tomcat7:run
```

**Task 02** Open the project in your favorite IDE. Inspect all the files in the application and decide what is their purpose. The application contains:
 * a [ServletContextListener](https://docs.oracle.com/javaee/7/api/javax/servlet/ServletContextListener.html) in the class MyStartInitializer
 * a [Filter](https://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html) in the class CharacterEncodingFilter
 * a [Servlet](https://docs.oracle.com/javaee/7/api/javax/servlet/Servlet.html) in the class HomeServlet
 * a localization [ResourceBundle](https://docs.oracle.com/javase/9/docs/api/java/util/ResourceBundle.html) in the files Texts*.properties
 * some JSP pages in the *.jsp files
 * a cascading stylesheet in the file style.css
 * an image in the file favicon.ico used as an icon in browser tabs and bookmarks
 * two custom JSP tags in the *.tag files
 * the configuration file logback.xml for the Logback logging framework
 * libraries for JSTL (JSP Standard Tag Library), SLF4J and Logback 

Change the setting of your browser accepted language subsequently to Czech and English and check whether everything in the application is properly localized in both languages.

**Task 03** Add a logging statement to CharacterEncodingFilter to log the called URL on the "trace" level. Modify the logback.xml file to see the log events. Re-run the application and check the log.

**Task 04** Click on the "to Prague" link in the navigation menu. Go back and click on the direct link to praha.jsp on the home page. Explain the difference of the output. (If not sure, check the class javadoc in the [solution](https://github.com/fi-muni/PA165/blob/seminar-web1/webapp-plain/src/main/java/cz/muni/fi/pa165/web/PrahaServlet.java)). Move the file praha.jsp to the WEB-INF/hidden-jsps/ folder so that it cannot be called directly, and modify PrahaServlet class to forward to the new location of the JSP.

**Task 05**  Make all changes needed for the third item in the navigation menu to work. It means - create a servlet, a JSP page, and localized texts in the Texts*.properties files 

**Task 06** Modify the page template in the file pagetemplate.tag so that every page has a footer which says `&copy; Masaryk University`. Modify the CSS stylesheet in the file style.css so that the footer has light blue background color. (You may need to force cache refresh in your browser to see the CSS change.)

**Task 07** Create a new filter mapped to every request that adds a request attribute with key "currentyear" and a string value with the current year number (i.e. 2017). Modify the page template pagetemplate.tag so that the page footer copyright message includes the attribute value.

(Hints:
[ServletRequest.setAttribute(String,Object)](http://docs.oracle.com/javaee/7/api/javax/servlet/ServletRequest.html#setAttribute-java.lang.String-java.lang.Object-))
[SimpleDateFormat(String,Locale)](http://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html#SimpleDateFormat-java.lang.String-java.util.Locale-)
[ServletRequest.getLocale()](https://docs.oracle.com/javaee/7/api/javax/servlet/ServletRequest.html#getLocale--)) )

**Task 08** Check out tag seminar-web1_step2 from the repository, forcing the reset of files (your changes will be deleted):
```
git checkout -f seminar-web1_step2
```
New files will appear:
* a servlet class PharmacyServlet with an inner class Drug
* a JSP page pharmacy.jsp 

Run the updated application and add some data using the form. Inspect how the form works.

**Task 09**
Add a third property *vendor* to drugs, i.e. add it to the javabean, the form and to the table.
 
**Task 10** Let's try some attack. Enter the value 
`<script>location.href='http://seznam.cz/';</script>` 
for the name of the drug and submit the form. This was a successful Cross Site Scripting (XSS) attack. Prevent the attack.

**Task 11** Check out tag seminar-web1_step3 from the repository:
```
git checkout -f seminar-web1_step3
```
There is a new class ProtectFilter. Run the application and visit the page with the form again. Provide the correct username and password. This is the HTTP BASIC authentication which pops up a standard browser dialog window that cannot be modified. 

**Task 12** Check out tag seminar-web1_step4 from the repository:
```
git checkout -f seminar-web1_step4
```
This is an example of a container-managed form-based authentication. There are a number of changes:
 * a new file *tomcat-users.xml* contains the definition of users, their passwords and security roles
 * the file *pom.xml* was modified to use this file in tomcat7 plugin
 * a new file *web.xml* contains the definition of protected URLs and the method of authentication
 * two new JSPs *login.jsp* and *login-failed.jsp* contain login form and failure message respectively
 * resource bundles Texts.properties and Texts_cs.properties contain new keys for the new JSPs
 * the JSP *podoli.jsp* was modified to display info about authenticated user 

Run the application **from the command line** (otherwise the tomcat-users.xml file will not be used). Click on *to Podolí* and authenticate using the form. 
Define a new user in a new role, and secure the URL `/snoop.jsp` to be accessible only by this new user.

**Task 13** In this task we will try a SAML (Security Assertion Markup Language) federated identity login. However establishing mutual trust among involved parties needs administrative steps and takes time, which we cannot afford during this seminar, so we will use pre-existing systems with already established legal relationship, in this case *Google Apps for Education* as the service provider and *IS MU* as the identity provider.

Open an anonymous window in your browser (it clears all cookies and
authenticated sessions). In the anonymous window, open the URL
[https://accounts.google.com](https://accounts.google.com) and specify **`your_učo@mail.muni.cz`** as your email.
You will be redirected to the login page of IS MU, log into it with your
učo and primary password. If you have not allowed Google Apps in External
services in IS before, do it now (you can switch it off again at
[https://is.muni.cz/auth/extservices/](https://is.muni.cz/auth/extservices/)
later). Then you will be redirected back to Google, where the account knows your name and email address from IS.

**Task 14** Check out tag seminar-web1_step5 from the repository:
```
git checkout -f seminar-web1_step5
```
In this task we will try OAuth2 authentication. You will need a Google account, use either your own or the one created in the task 13.
Run the web application **from the command line**, it is important to have it running on the URL http://localhost:8080/webapp-plain !

The home page now has an icon for logging in using Google Account, and a new servlet GoogleServlet for processing the authentication.
Log in using Google account, the home page should display your name, email and picture. You can revoke the permission for the application at [Apps connected to your account](https://security.google.com/settings/security/permissions).
