package com.bank.app.cards_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI documentation for the Card Service application.
     * @return an OpenAPI instance with custom information.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Card Service API")
                        .version("1.0")
                        .description("API documentation for the Card Service application"));
    }
}