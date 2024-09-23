package com.tripmate.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .version("1.0.0")
                .title("Tripmate API")
                .description("Tripmate API Swagger Documentation");

        Server local = new Server()
                .url("http://localhost:8080")
                .description("Local Server");

        Server production = new Server()
                .url("https://tripmate.life")
                .description("Production Server");

        return new OpenAPI().info(info).servers(List.of(local, production));
    }
}
