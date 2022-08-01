# Spring MVC Example
Based on [SpringMvcStepByStep](https://github.com/in28minutes/SpringMvcStepByStep). Developed as Spring Boot application, added a Postgres DB and Hibernate for JPA, tested using Unit and Integration tests, monitored by application and performance logs using log4j and AspectJ and deployed to a docker.<br/>
Use `mvn clean package` to package the application in a war file (or `mvn clean package -DskipTests` if the db is not installed locally).<br/> 
Use `docker-compose up -d` command to run the app and `docker-compose stop` to terminate it.
