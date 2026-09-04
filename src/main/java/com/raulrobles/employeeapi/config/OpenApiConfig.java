package com.raulrobles.employeeapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("Employee API")
                .version("1.0.0")
                .description("REST API for employee management.")
                .license(new License().name("Internal evaluation project")));
    }
}
