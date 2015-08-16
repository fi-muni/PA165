## Persistence Seminar 01
Your first tasks will be related to module persistence-app. It will teach you basic work with application managed EntityManager and it's configuration.

**Task 01** Your first task is to locate and download JPA 2.1 specification (JSR 338). It is a PDF file. 
   
**Task 02** Try to run cz.fi.muni.pa165.MainJavaSe from NetBeans 

**Task 03** Run the main method also  from command line (using Maven exec:java 
target) see documentation for Maven here http://mojo.codehaus.org/exec-maven-plugin/java-mojo.html

**Task 04** Add configuration property to persistence.xml so that Hibernate writes all generated SQL statements to console. See A.2 section of
https://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html_single/ . Rerun **Task 03** to confirm that you can now see the SQL

**Task05** Put call to a method 'task05' under line "// BEGIN YOUR CODE". Implement TODO in task05 method. If you do everything correctly, you will see the following output "Succesfully found Electronics and Musical!" after you run the Main Method. If not you should debug your solution and find out why it doesn't work.

**Task06** This task requires you to work with a detached entity. To start working on a task, just add call to task06() method into your main method. Then implement the task06() according the comments in there.



**Task 18** Quiz. You can check your answers after you take the quiz in file answ-q1.md 
 1. What is the main configuration file for JPA in your application?
 2. Where is the following text used and what is the effect of it (use Hibernate dev guide to find answer)? "hibernate.format_sql"
 3. What is hibernate.hbm2ddl.auto property in persistence.xml file?

 


