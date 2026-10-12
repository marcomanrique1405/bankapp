package com.marco.bankapp.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bankAppOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("BankApp API")
                                .version("v1")
                                .description("Banking REST API built with Spring Boot")
                );

    }

}
