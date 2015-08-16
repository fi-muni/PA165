1. Its the side of the relationship that dictates updates to the database.
2. The relationship in the example is bidirectional one-to-many. Hence the owning side must be (from the definition in the spec) the Product side (which is the Many side)
3. META-INF/persistence.xml
4. persistence.xml. It is used to instruct Hibernate to pretty print the SQL statements that it generates. This helps debugging because such SQL statements are easy to read.
5. It is a property that sets the strategy for manipulating the underlying RDBMS. If set to create-drop, the database is dropped and recreated with each application startup. The creation is according to the entities that are present in the JPA application. If set to update, the database schema is only updated when Entities are changed.   
