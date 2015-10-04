1. http://docs.oracle.com/javaee/7/api/javax/persistence/CascadeType.html
2. After close() method is called, the objects are moved to DETACHED state. Nothing is propagated into the database, so nothing happens from the point of the view of the database.
3. The changes are propagated to the database when transaction commits or flush() is called!
4. It is always possible in persistence.xml. However, there are also other means to configure this such as Spring configuration.
5. Yes, there is so called Criteria API. This wasn't covered by the seminar.
6. Short correct answer is 0. Howerver, this assumes the associations are all LAZY. In extreme situation, when developer set all the associations to EAGER, the query would return all the line items from the whole database. This, of course, is performance problem and this shows that EAGER fetch must be avoided on OneToMany associations if possible. 
