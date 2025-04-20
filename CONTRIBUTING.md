# Contributing to [pomodoro-timer-api](https://github.com/naedher/pomodoro-timer-api)
## Table of Contents

- [Introduction](#introduction)
- [Getting Started](#getting-started)
- [Tools](#tools)
- [Branching Strategy](#branching-strategy)
- [Code Style Guide](#code-style-guide)
- [Design](#design)
- [Pull Request Process](#pull-request-process)

## Introduction
This project aims at building a REST API that handles authentication, data validation and communication with the PostgreSQL database for a larger project [pomodoro-timer](https://github.com/naedher/pomodoro-timer).

This document aims at getting developers wanting to contribute familiar with the tools, guidelines and principles we are using.

## Getting Started

To set up your development environment:

- Fork the repository and clone your fork:
```bash
   git clone https://github.com/naedher/pomodoro-timer-api.git
   cd project-name
```

As a prerequisite for this project, Java 17+ is required.

### Local files

To be able to run the program locally you need to create some files.
#### Config files
##### Database connection
1. In `src/main/resources` create a file named "datasource.properties"
2. Fill out the file according to the following format.
```properties
spring.datasource.url=jdbc:postgresql://pgserver.mau.se/_pomodoro
spring.datasource.username=your-pgadmin-username
spring.datasource.password=your-pgadmin-password
```

##### JWT Secret
1. In `src/main/resources` create a file named "jwt-secrets.properties"
2. Generate a JWT secret, we used [JWT Secret Key Generator](https://jwtsecret.com/generate)
3. Fill out the file according to the following format.
```properties
jwt.secret=your-secret-key
```

#### Formatting and Linting
To make formatting and linting work, each developer needs to edit their `~/.m2/settings.xml` file by adding:

```xml
<pluginGroups>
	<pluginGroup>io.spring.javaformat</pluginGroup>
</pluginGroups>
```

If you do not have this file, create it and paste this:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
  <pluginGroups>
	<pluginGroup>io.spring.javaformat</pluginGroup>
  </pluginGroups>
</settings>
```


## Tools
This section talks about the tools we have chosen and some references to get you started.
### Spring Boot
Spring is the main framework we are using and it handles most of the application layer by instantiating objects, injecting dependencies and setting up the whole MVC pipeline.
To learn more about Spring Boot, check out the Spring Boot [documentation](https://docs.spring.io/spring-boot/index.html) or to just get a taste, check out [this quickstart guide](https://spring.io/guides/gs/rest-service). For a more in depth guide, check out the [Spring REST](https://spring.io/guides/tutorials/rest) tutorial. Also, Baeldung has an excellent series on [Rest With Spring](https://www.baeldung.com/rest-with-spring-series).

The full Spring Boot API can be found [here](https://docs.spring.io/spring-boot/api/java/index.html).

### Spring Data JPA
This is the data access layer of the application and it handles the repository layer and sending queries to the database. Spring Data JPA automatically wires this up for us and makes the communication to the database completely transparent through the `JpaRepository` interface.

To learn more about Spring Data JPA, check out the Spring Data [documentation](https://docs.spring.io/spring-data/jpa/reference/). There is also the Baeldung series on [Spring Data](https://www.baeldung.com/spring-data).

### Lombok
Lombok is more of a utility package that removes the need for much of the boilerplate code one must write to build a java application. The whole package only contains a few annotations and for our purposes we mostly use it to slim down entity classes and to create standard constructors.
To learn more, check out the Lombok [documentation](https://projectlombok.org/features/).

> Be careful when adding Lombok annotations! Lombok has a tendency to break JPA managed classes such as entities.

1. `@EqualsAndHashCode` that can break entity identity.
2. `@ToString` can cause stack overflows in bidirectional relationships.
3. `@Data` includes both `@EqualsAndHashCode` and `@ToString`.
4. Entities created with `@Builder` can bypass validation and lifecycle events.
5. Adding multiple constructors to a `@Component` or any of it's inheritors such as `@Sevice` or `@Controller` can break Spring's dependency injection. Make sure there is only `@RequiredArgsConstructor` on these classes or annotate dependencies with `@Autowired`.

Using `@Data` and `@Builder` is acceptable for non-entity classes such as DTOs.
### Maven
Maven is the main build tool of this project. It helps us manage project builds, compile code and include dependencies through a configuration file called `pom.xml`. 
There is no need to install maven manually since we have a maven wrapper named "./mvnw".

- To run the program type:
```bash
./mvnw spring-boot:run
```

### Spring Security
This package is part of the wider Spring ecosystem and contains ways to secure spring applications. In this project, it handles the authentication of users and makes sure that users can only access resources owned by them. A brief introduction to how the authentication flow is structured is explained in the [Architecture](https://docs.spring.io/spring-security/reference/servlet/architecture.html) section in the Spring Security [reference docs](https://docs.spring.io/spring-security/reference/index.html). For our purposes, the [Username/Password](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html) section is also very useful.

### Java JSON Web Tokens (JJWT)
[JJWT](https://github.com/jwtk/jjwt) is a package that handles the creation and validation of JSON Web Tokens (JWT). These tokens are what is able to securely identify a user over the web. They are basically just JSON encoded into a string by a secret key. If you want to learn more about JWTs there is a good explanation in the JJWT readme under [What is a JSON Web Token?](https://github.com/jwtk/jjwt?tab=readme-ov-file#what-is-a-json-web-token).


## Branching Strategy

We follow [GitHub Flow](https://githubflow.github.io/):

1. Create a new branch off `main`:
```bash
git checkout -b your-branch-name
```
2. Make your changes.
3. Push to your branch.
4. Open a pull request into `main`.
5. Give it a [description](https://google.github.io/eng-practices/review/developer/cl-descriptions.html).
6. Request a contributor, ideally someone that is closely involved with the changes.

Each PR should be small, focused, and ideally linked to an issue.

## Code Style Guide
We use the [Spring Framework Code Style](https://github.com/spring-io/spring-javaformat) conventions.
### Linting and Formatting

We use Checkstyle to enforce code style.

- Run the formatter before committing:
```bash
./mvnw spring-javaformat:apply
```
- Run the linter to validate the code style:
```bash
./mvnw spring-javaformat:validate
```


## Design
### MVC Structure
#### Model
The model or entity layer is responsible for modeling the tables and relationships present in the database. These are used by the repository to map between objects in the API and rows in the database. 

#### Repository 
The repository layer is responsible for all the operations that directly accesses the database. In our case these operations are abstracted by [JPA](#spring-data-jpa). If you need a custom operation, then define it as a [Query Method](https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html).

#### Service
The service layer handles all the business-logic and acts as a layer between the controller and repository. Here most of the data validation happens and the service should throw suitable exceptions to be caught by the controller if something is wrong. The service also handles mapping from DTOs to entities.

#### Controller
The controller defines all the endpoints that are available to the outside world and defines the structure these requests should have.
It also handles sending suitable responses depending on what happens in the other layers. Through the use of exceptions the controller can know how to respond.

### Data Transfer Objects (DTO)
DTOs are simple objects used to transfer data to and from the API and the outside world. 
They can also be used to handle data transfer between different layers within the API.

#### Example DTO
```
public class TimerDto {
    private Long id;
    private String name;
    private Integer workDuration;
    private Integer breakDuration;
    private Integer pomodoroCount;
	
	// Constructors
	// Getters & Setters
	// ...
}
```
#### Naming DTOs
##### Inbound
`{ClassName}{Operation}Request
Ex. 
`TimerUpdateRequest`
`SessionDeleteRequest`
##### Outbound
`{ClassName}{Content}Response`
Ex.
`TimerDetailsResponse`
`SessionHistoryResponse`
##### Internal
###### Single DTO
 If there is only a single DTO associated with an entity it is okay to name it after the entity.
`TimerDto`, `SessionDto` etc.
###### Multiple DTOs
If there are multiple DTOs used internally related to a single entity then name it after what it contains, similar to outbound DTOs.

#### Mapping
We are using [ModelMapper](https://modelmapper.org/) for mapping between Entities and DTOs.
ModelMapper is configured in `ApplicationConfig`. There we initialize the object and add any potential property mappings.

To have good separation of concerns regarding DTOs we can also create an explicit mapper class that defines the allowed mappings and handles the usage of the ModelMapper instance instead of using it directly in the service.

## Pull Request Process
To learn more about how to handle the pull request process, read the [Google Engineering Practices](https://google.github.io/eng-practices/).

