## Persistence Seminar 02
Do not forget to checkout branch seminar05! Do not work in master! In this seminar you will work with unit tests in eshop-persistence module. There is package cz.fi.muni.pa165.tasks in test directory. Each task is associated with one unit test in the package.

**Task 01**  
Modify class Task01Test to be unit test that creates ApplicationContext. Use @ContextConfiguration annotation (see documentation [1][2]) and configure it so that PersistenceSampleApplicationContext.class is used as the spring configuration class. Your test class should also extend AbstractTestNGSpringContextTests. Insert the following code and run the test:

```java
	@PersistenceUnit
	private EntityManagerFactory emf;

	@Test
	public void categoryTest() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Category cat = new Category();
		cat.setName("Test");
		em.persist(cat);
		em.getTransaction().commit();
	}
```

(You can copy-paste test from this task (and just rename it) for all the following tasks.)

Also, add assert to this test. Create a second entity manager in categoryTest, use find method to find the category and assert its name.

**Task 02**
Create bidirectional relationship many to many between Product and Category. Product can be placed in many categories and many categories in many products. Create java.util.Set on each entity and use ManyToMany annotation. Set Category as owning side. Owning side was covered on the lecture, if you are unsure what it is you can find it in JPA specification or in some tutorials on the internet.
Then write a unit test Task03 that tests this works. In the test @BeforeClass first create two categories Electro and Kitchen. Then, still in @BeforeClass create 3 products: Flashlight, Kitchen robot, Plate. Place them in the appropriate categories and commit transaction. Then write 5 unit tests. One for each category and one for each product. In each test create a new entity manager, search for the entity and assert that it has correct content of java.util.Set

**Task 03**
Create DAO object for Product. You will need to create an interface ProductDao and implementation ProductDaoImpl. Implement findAll, findById, create, delete. To get entity manager inside DAO use @PersistenceContext. EntityManager that is created this way is called Container managed EntityManager. Do not create the entity manager through EntityManagerFactory as in the previous task! Such an EntityManager would be Application Managed.
To implement findAll you will need to use JPQL this is what you need:
```java
   em.createQuery("select p from Product p", Product.class).getResultList();
```
Also, you must annotate the DAO with @Transactional since you want every method to be transactional.
Now, just for testing this out, write one long unit test method (this is not a good practice!). In this test, inject your DAO into the test using @Autowired annotation. Use create method to create two products. Then try findAll, findById and remove.

**Task 04** 
Write JPQL queries and criteria API queries for tests in Test04.java The Test file  contains comments that ask you to write queries.

**Task 05**
Use beans validation to validate Product. Use javax.validation.NotNull annotation. Then write 1 new unit tests into the test from **Task 02** to verify your validations work. One of the unit tests will try to persist product with null name. This test should expect exception. 

[1] http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/
[2] http://docs.spring.io/spring/docs/current/javadoc-api/
