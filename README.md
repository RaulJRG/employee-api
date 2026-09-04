## About the project

This project was developed as part of a technical evaluation for a Senior Java Developer position. It provides a REST API for employee management, focusing on clean code, validation, error handling, API documentation, and unit testing.

## Technologies requirements

Spring version 2.7.18

Note: The project template was downloaded initially with the spring boot version 4.0.8, but in order to accomplish the specification of this test, it was downgraded to 2.7.18 and some modules were adapted in the pom.xml

Java 17

Maven 3.9.16

MySQL 8 (with the prod profile)


## Compile and run the project

After some dependencies modifications in pom.xml, compile using the next command (for Windows):

```
.\mvnw.cmd clean compile
```

To start de application, run the next command:

```
.\mvnw.cmd spring-boot:run
```

Tomcat will start by default in port 8080, so you can test the api using the base url

```
http://localhost:8080
```

## Build and test

On Windows, use the Maven Wrapper included in the repository:

```
.\mvnw.cmd clean test
```

## API documentation

The OpenAPI contract was generated with springdoc, it can be accessed here:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

You can also find the yaml downloaded here:

`docs\openapi\employee-api.yaml`

And the postman collection in

`docs\postman\Employee Management - Test.postman_collection.json`

## About the database

The prod profile is prepared for an external MySQL instance.

Provide the database connection variables and activate the profile using environment variables:

```
$env:DB_URL = "jdbc:mysql://localhost:3306/employee_db?useSSL=false&serverTimezone=UTC"
$env:DB_USERNAME = "your_database_user"
$env:DB_PASSWORD = "your_database_password"
$env:SPRING_PROFILES_ACTIVE = "prod"

.\mvnw.cmd spring-boot:run
```

The production profile uses `ddl-auto=validate`, so the database schema must already exist. The development profile uses the recommended option for the technical evaluation (with a h2 database).

## Evidences

In order to validate de correct functionality of this application, some images were attached in the path `evidence/`

- Services
For endpoints succes and error response, the images can be found in `evidence/postman`

- OpenAPI documentation
This can be found in `evidence/openapi`

- Unit tests
The result of a test execution successful is here `evidence/tests`
