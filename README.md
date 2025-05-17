# Pomodoro Desktop Timer API

## Introduction
This project aims at building a REST API that handles authentication, data validation and communication with the PostgreSQL database for a larger project [pomodoro-timer](https://github.com/naedher/pomodoro-timer).

This document aims at getting developers wanting to contribute familiar with the tools, guidelines and principles we are using.

## Getting Started
To set up your development environment:

- Fork the repository and clone your fork:
```bash
   git clone https://github.com/naedher/pomodoro-timer-api.git
   cd pomodoro-timer-api
```

As a prerequisite for this project, Java 17+ is required.

### Running locally
To be able to run the program locally you need to create a special file that contains some important information.

1. In `src/main/resources` create a file named "development.properties"
2. Fill out the file according to the following format.
```properties
spring.datasource.url=jdbc:postgresql://pgserver.mau.se/_pomodoro
spring.datasource.username=your-pgadmin-username
spring.datasource.password=your-pgadmin-password

jwt.secret=your-secret-key
```

##### How do I get a JWT Secret?
1. Run the `KeyGenerator` file located in the `config` folder to generate a JWT secret.
2. Replace `your-secret-key` in the `development.properties` file with your generated token
```properties
jwt.secret=your-secret-key
```

### Sending HTTP Requests
To send requests to the API you can use whatever means you are familiar with but here is an example of a request sent using curl:

```bash
curl -X 'POST' http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{"email": "testperson2@gmail.com", "password": "password123"}'
```

### API documentation
The documentation of the available endpoints and what format they expect is available [here](https://pomodoro-timer.koyeb.app/swagger-ui/index.html#/).

### Contributing
For more information about the project, read the [CONTRIBUTNING.md](CONTRIBUTING.md) file.
