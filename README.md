## Example using Vertx 4.2.3, MySql 8.0.27, PostgreSQL 12.9-1, and Oracle 19.3 (19c)


### Used technology stack in this example

	1. Hibernate-6.0.0.Beta3
	2. Jakarta Persistence 3.0.0
	3. OpenJDK 17
	4. Apache Maven 3.8.3
	5. MySql 8.0.27
	6. PostgreSQL 12.9-1
	7. Oracle 19.3 (19c)

Note: This example is not using Java Persistence API.


### This example work with MySQL, PostgreSQL, and Oracle

	- Only modify the hibernate.properties to work with your database.
	- Create DB schema name as "mycompany" before run this example for oracle 19c
	- Create DB schema name as "mycompany" before run this example for PostgreSQL
	- Create DB schema name as "mycompany" before run this example for MySQL
	

### Maven initial setup

	$ mvn dependency:tree
	$ mvn eclipse:eclipse


### Build application jar or war

	$ mvn clean package
		

### Run the following java programes

	$ App.java
	
	