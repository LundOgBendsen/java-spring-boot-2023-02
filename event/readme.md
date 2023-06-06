# Spring Boot and Swagger integration, demo

This is a demontration of Spring Boot and how to generate Swagger documentation for a REST API using SpringDoc.

## How to generate swagger using Maven
* Run  `mvn install` to build your application which includes generating a, events.json file in the target-folder. 

## How to see the swagger-ui
* Start application with `mvn spring-boot:run` or run the class dk.logb.spring.boot.swagger.demo.event.EventApplication
* Go to [/swagger-ui](http://localhost:8080/swagger-ui/index.html) page on the local machine 

## Relevant Articles
* [Baeldung](https://www.baeldung.com/spring-rest-openapi-documentation)

## What to look for
* You can try out the API from [Swagger-UI](http://localhost:8080/swagger-ui/index.html#/event-controller/getAllEvents). 
Click the "Try it out" button and then the "Execute" button. 
* The swagger documentation is automatically generated from the code. See the EventController and Event classes.
* The SpringDoc plugin is aware of SpringData and pagination. Look at the EventController.filterEvents method. 
Pagination parameters are automatically added to the swagger documentation.
* The SpringDoc plugin is aware of JSR-303 validation. Look at the Event class which has @NotBlank and @Size annotations.
These constraints are automatically added to the swagger documentation.
* The io.swagger.v3.oas.annotations package contains annotations that can be used to customize the swagger documentation. 
See the EventController.getEvent(int) method which has a @Operation annotation. You can se the result in the swagger documentation 
or [Swagger-UI](http://localhost:8080/swagger-ui/index.html#/event-controller/getEvent)
* 