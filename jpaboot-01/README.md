# Spring boot JPA Demo

## Model

```plantuml
@startuml
skinparam class {
    BackgroundColor FloralWhite
}

class Person {
    id : integer
    name : string
    address : string
    created : timestamp
}

class Orders {
    id : integer
    order_date : date
    number : string
}

class OrderLine {
    id : integer
    quantity : decimal
    price : decimal
}

class Product {
    id : integer
    name : string
    price : decimal
}

class ProductType {
    id : integer
    name : string
}

class Supplier {
    id : integer
    name : string
    address : string
}

Product  "*" <--> "*" Supplier
Product  "*" *--> "1" ProductType
Person  *--> "0..*" Orders
Orders  *--> "0..*" OrderLine
OrderLine  *--> "1" Product

@enduml
```


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#howto.data-initialization.migration-tool.flyway)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

