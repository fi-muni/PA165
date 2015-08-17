## Seminar 02 30.9.2014
In this seminar you will mostly work with unit tests. These three tests are the most importatnt for you: 
AppManagedEntityManagerTest, ContainerManagedEntityManagerTest, ProductDaoTest. The tests are documented, read through the comments before you begin your tasks.

**Task 01** First run the following single test method from the IDE:  AppManagedEntityManagerTest.persistAndFindProduct()

It should pass 
```
Default suite
Total tests run: 1, Failures: 0, Skips: 0
```  

**Task 02** Try to run the test from **Task 01** through Maven from command line. To do this you should use "mvn test" command with a correct parameter. Find the parameter in maven documentation http://maven.apache.org/surefire/maven-surefire-plugin/test-mojo.html   

Obviously you should get the same result as in **Task 01** . 

**Task 03** There is a new entity in the project PetStore. Map it as an entity and check that it can be stored in the database by running test PetStoreMappingTest.testSimplePersist 

**Task 04** Uniqueness. Add new field String textIdentifier to the PetStore, there is a Java doc in the PetStore.java that explains the requirements for this field.
Check the correctnes with tests: 
   PetStoreMappingTest.identifierMaxLenIs10
   PetStoreMappingTest.uniqueTextIdentifier 

**Task 05** Embedded mapping. Create Embeddable class Address and map it using annotation @Embedded as a part of the PetStore (its commented out in the PetStore entity right now). Uncomment code that is commented out in test  embeddedAddressTest  and then run the test

**Task 06** Embedded element collection mapping. Add a collection "Set<Address> previousAddresses" to the PetStore. Do not map this as one to many but instead use ElementCollection and reuse the Address that you created in **Task 05**
Also set fetch type to "fetch=FetchType.EAGER" so that the previous addresses are always fetched together with a PetStore
Again uncomment the code in embeddedOldAddressesTest and run the test

**Task 07** Map the following fields: dateOfOpening, openTime,closeTime. The dateOfOpening should be only date (e.g. 24-9-2014) without time information. The openTime and closeTime should be stored only as time information (e.g. 13:30:44) 
Run tests dateOfOpeningHasNoTime, openTimeAndCloseTimeHasNoDate

**Task 08**  Before running this task, issue "mvn clean install -DskipTests", we had a few issues with this one. This task requires you to work with LoadStateTest.java.  There is a comment in the eagerFetchTest that requires you to add a new assert. Firstly enable the test (@Test(enabled=true))and then add the assert.

Run the test, you should get a assert Exception

```
java.lang.AssertionError: expected [LOADED] but found [NOT_LOADED] 
```


**Task 09** Now you know that the cages collection is not loaded. To pass the assert you created you need to change the fetch type for the cages association to EAGER, do it and rerun the test.
It will still fail because the test traverses all the pets in the cages. Fix this by adding eager fecth also on that association.

**Task 10** Now the test eagerFetchTest passes. Answer these questions: How many queries are sent to the database? How many entities are retrieved from the database during this test?

**Task 11** Eager fetch setting on the associations such as OneToMany are very usually a bad practice. Remove EAGER fetch from PetStore.cages and Cage.pets. Disable the eagerFetchTest (use @Test(enabled=false)) and implement two unit tests in LoadStateTests that will check that after loading a Cage or a PetStore, the collections are not loaded.
 

**Task 12** This task requires you to work with EntityLifecycleTest. You can see that entityDetachTest fails. We modify detached entity by setting name "Honza" and we would like that to propagate into the database. Use EntityManager.merge method instead of the comment "//ASSOCIATE HERE" to get the name changed in the database 

**Task 13** Change the name again to "Marek" on line commented with "//CHANGE MANAGED ENTITY HERE". Be careful, the merge method that you used in **Task 12** will NOT MAKE the argument that you passed to the merge method MANAGED. The merge method RETURNS a new instance that is MANAGED. If you done everything correctly, can see the changes will be propagated into the database because the entity is in MANAGED state.  

**Task 14** Modify the entityRemove test on line marked "		//DELETE THE PET HERE". Delete the pet with id "pet1Id". The test should pass after your changes.

**Task 15**  JPQL. You will work with JpqlTest.java. Your task is to fix all the tests by only rewriting the JPQL queries (the JPQL query is the argument in every em.createQuery method). There are lot of hints in the comments in the test methods. When solving each test method read them carefully. 

**Task 16**  Cascading. Many operations in JPA (e.g. persisting, removal) can be cascaded, meaning that the operation is propagated through a relation. You will work with test EntityLifecycleTest.persistCascade. When you run the test you should see the following problem:

```
object references an unsaved transient instance - save the transient instance before flushing
```

The reason is that we havent persisted the Cage. However, because the Pet is the owning side of the relationship, we can set PERSIST cascading to make this code work. Go to Pet.cage field and change the annotation to this: @ManyToOne(cascade=CascadeType.PERSIST).

Then try to run the test. 

**Task 17** Quiz. You can check your answers after you take the quiz in file answ-q1.md 
 1. What is owning side of a relationship? Read through JPA spec, section 2.9 to answer this question. 
 2. Which side of the relationship in our example is the owning side?
 1. What kind of cascading types exist?
 2. What happens when you change some property on entity after EntityManager.close() method is called? 
 3. What happens if you change some property and entity is in the state MANAGED?
 4. Where is it possible to change connection string to a database that you want to access through JPA?
 5. The JPQL is purely string based. This may be dangerous sometimes. Is there any way to mitigate this and use some more statically typed API?
 6. Assume there are three entities in your application: Company, Order, Item. The Company places many orders and each order consists of many items. There are 3 companies in the database first two have 100 orders each and the last has 30 orders. Each order contains approximatelly 50 line items. Bidirectional JPA relationships are set up among the entities. Suppose somebody issues the following JPQL query "SELECT c FROM Company c". How many line items will be retrieved by this simple query from the actual database immediatelly after calling getResultList()?

 
**Task 18** 
As homework, solve the following two assignments  https://is.muni.cz/auth/el/1433/podzim2014/PA165/um/cv/2012_PA165_cv02.pdf https://is.muni.cz/auth/el/1433/podzim2014/PA165/um/cv/2012_PA165_cv03.pdf  in a new Java project. Consider downloading the .pdf files in case you are not able to copy the xml snippet from the browser pdf reader.

 
**Task 15** Make sure you maintain runtime consistency of your relationships. 
It means that when you add a category to a pet through Product.setCategory() method, you also add the Product to the collection held in the Category entity. In other words, you maintain the relationship from both sides. This is very important and JPA spec requires the developer to do this!
 **Task 12** Perfect, now we have unidirectional ManyToOne relationship where Product has a reference to a Category. We would like to create this relationship bidirectional (so that Category also knows all Products). Open Category entity and remove @Transient annotation. Instead add @OneToMany(mappedBy="cage").

**Task 16** You can see that the output shows that Product have addedDate also with time. This time part of the information is really not necessary. Change the annotation on birthDate field in Pet so that the date is stored in the database only with Year, Month, Day components. Hint: Take a look at Temporal annotation  

** Task 19 ** Our relationship between Category and Product is OneToMany. Change this to ManyToMany and the owning side should be the Product side. You will have to add the following methods: Product.addCategory, Product.removeCategory, Product.getCategories, Category.addProduct,Category.removeProduct, Category.getProducts. Maintain runtime consistency.
**Task 09** Adding relationships. Now we have Categories and we have Products. We would 
like to set category for a Product. To be able to do so you need to map the relationship. In Product entity, remove @Transient and replace it with @ManyToOne. Rerun **Task 03** to verify everything works fine so far. Now in your main method use setCategory on some of your products to add the Category to the Product. 

**Task 10** To understand transient instances, try code such as this: 
```java
		em.getTransaction().begin();
		Product p = new Product();
		p.setName("Speakers");
		Category category = new Category();
		category.setName("sound");
		product.setCategory(category);
		em.persist(product);
		em.getTransaction().commit();
```

You will get TransientPropertyValueException. Why is that? Its because your Category is not persisted. Fix this.


**Task 11** We now can set a Category for a Product. Let's output this fact to the console. Modify your printAllProducts to not only show all Products but also for each Product print out category of the product.


   