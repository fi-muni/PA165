1. persistence.xml
2. persistence.xml. It is used to instruct Hibernate to pretty print the SQL statements that it generates. This helps debugging because such SQL statements are easy to read.
3. It is a property that sets the strategy for manipulating the underlying RDBMS. If set to create-drop, the database is dropped and recreated with each application startup. The creation is according to the entities that are present in the JPA application. If set to update, the database schema is only updated when Entities are changed.   
