## Persistence Seminar 01
Do not forget to checkout branch seminar-persistence1! Do not work in master! Open eshop-persistence maven module in your IDE. This first persistence seminar tries to be as minimalistic as possible. You will work with in-memory database and just one Main Class (MainJavaSe.java). You will be asked to implement parts of methods. Then you will run the method by modifying the Main method. For example if I you want to try method corresponding to **task07**, then you will modify the main method this way:
  ```java
		emf = Persistence.createEntityManagerFactory("javaSeUnit");
		// BEGIN YOUR CODE
		task07();
		// END YOUR CODE
		emf.close();
  ```

If you experience out of memory errors, you can use the following command in terminal to kill all java processes:
```
killall java
```

**Task 01** Your first task is to locate and download JPA 2.1 specification (JSR 338). It is a PDF file. 
   
**Task 02** Try to run cz.fi.muni.pa165.MainJavaSe from NetBeans. It runs method task04(). You should get runtime exception saying that it is expected you have 2 Category entities stored in the database. 

**Task 03** Add configuration property to persistence.xml so that Hibernate writes all generated SQL statements to console. You may have to modify log4j.xml as well (in resources dir). See A.2 section of
https://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html_single/ . Rerun **Task 02** to confirm that you can now see the SQL. 

**Task04** Implement everything specified in TODO comments in task04() method. If you do everything correctly, you will see the following output "Succesfully found Electronics and Musical!" after you run the Main Method. If not, you should debug your solution and find out why it doesn't work.

**Task05** This task requires you to work with a detached entity. Implement the task05() according the comments in there.

**Task06**  Parts of the method are commented out, because the implementation of entity Product is not complete yet. Work according to the TODO in comments. Do not forget to solve 'Additional task' :-) 

**Task08** This task requires you to correctly implement equals and hashcode methods of Product entity. Note that you should use business equivalence. Look into the method comments for more instructions. 

**Task 09** Quiz. You can check your answers for this quiz in file answ-s1.md 
 1. What is the main configuration file for JPA in your application?
 2. Where is the following text used and what is the effect of it (use Hibernate dev guide to find answer)? "hibernate.format_sql"
 3. What is hibernate.hbm2ddl.auto property in persistence.xml file?

