## Spring version

The project template was downloaded initially with the spring boot version 4.0.8, but in order to accomplish the specification of this test, it was downgraded to 2.7.18

After some dependencies modifications in pom.xml, the project was recompiled by usgin the next command

.\mvnw clean compile

## Starting the project

Run the next command

.\mvnw spring-boot:run

Tomcat will start by default in port 8080, so you can test the api using the base url

http://localhost:8080

## API documentation

The OpenAPI contract was generated with springdoc, it can be accessed here:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

You can also find the yaml downloaded here:

docs\openapi\employee-api.yaml