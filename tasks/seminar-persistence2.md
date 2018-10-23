## Persistence Seminar 02
Do not forget to checkout branch seminar-persistence2! Do not work in master! In this seminar you will work with unit tests in eshop-persistence module. There is package cz.fi.muni.pa165.tasks in test directory. Each task is associated with one unit test in the package.

If you experience out of memory errors, you can use the following command in terminal to kill all java processes:
```
killall java
```

** Loading the projects ** 
After you checkout the branch, you must open the whole multimodule project into IDE, including the two modules eshop-api, eshop-persistence. If you experience problems, use mvn install ON WHOLE multimodule project from command line and rebuild projects in Netbeans

**Task 01**  
Modify class Task01 (package cz.fi.muni.pa165.tasks) in directory `src/test/java/cz/fi/muni/pa165/tasks` to be unit test that creates ApplicationContext.  
Use the `@ContextConfiguration` annotation (see documentation [1], [2]) and configure it so that `PersistenceSampleApplicationContext.class` is used as the spring configuration class.  
Your test class should also extend `AbstractTestNGSpringContextTests`.  
Test code is already in the file, you can run it and see if it doesn't fail.

Now you have running unit test, but it doesn't assert anything. Add assert to this test.  
Create a second entity manager in categoryTest, use find method to find the category and assert its name.

**Task 02**
Create bidirectional relationship many to many between Product and Category.  
Product can be placed in many categories and many categories in many products.  
Create `java.util.Set` on each entity and use `@ManyToMany` annotation.  
Set `Category` as owning side. Owning side was covered on the lecture, if you are unsure what it is you can find it in JPA specification or in some tutorials on the internet.  
Don't forget to clean up all TODO items in `Product` and `Category` entities.  
There are several TODO items that require you to either delete some code or uncomment some code.

Then you need to write tests into file Task02. In the `@BeforeClass` first create two categories Electro and Kitchen. Then, still in`@BeforeClass` create 3 products: Flashlight, Kitchen robot, Plate.  
Place them in the appropriate categories and commit transaction. Store these entity instances that you have created in `@BeforeClass` in instance variables, so that rest of the test method can access them.  

Then write 5 unit tests - one for each category and one for each product.  

In each test create a new entity manager, search for the entity and assert that it has correct content of e.g. `java.util.Set`.  

Hint: there are two helper methods prepared for you: `assertContainsCategoryWithName`, `assertContainsProductWithName`.

**Task 03**
Create DAO object for Product into package cz.fi.muni.pa165.dao. Interface ProductDao is already prepared for you, you need to implement only the implementation ProductDaoImpl. Use Repository annotation on the DaoImpl. Implement findAll, findById, create, remove. To get entity manager inside DAO use `@PersistenceContext`.   
 
To implement `findAll()`, you will need to use JPQL this is what you need:
```java
   em.createQuery("select p from Product p", Product.class).getResultList();
```

Then uncomment code in seminarservices.SeminarServiceImpl class (this one uses your DAO) and add @Transactional annotation to the Service.

Now, just uncomment Task03 unit test. 

Next try to implement test for ProductDao.remove operation.

**Task 04** 
Write JPQL queries and criteria API queries for tests in Test04.java The Test file  contains comments that ask you to write queries.

**Task 05**
Use beans validation to validate Product. Use javax.validation.NotNull annotation. Then write 1 new unit tests into the test from **Task 02** to verify your validations work. This test should expect exception so the signature will be something like this:
```java
	@Test(expectedExceptions=ConstraintViolationException.class)
	public void testDoesntSaveNullName(){
``` 

[1] http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/  
[2] http://docs.spring.io/spring/docs/current/javadoc-api/
