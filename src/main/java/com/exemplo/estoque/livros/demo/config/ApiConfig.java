package com.exemplo.estoque.livros.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API de Livros")
                        .description("RestAPI com tema livros")
                        .version("1.0"))
                .servers(Arrays.asList(new Server().url("http://localhost:8080-Generated server unl")));
    }
}
