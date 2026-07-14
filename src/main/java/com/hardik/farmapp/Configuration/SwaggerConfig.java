package com.hardik.farmapp.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI farmOpenAPI() {

        Info info = new Info()
                .title("Farm AI Recommendation API")
                .version("1.0")
                .description("""
                        AI-powered crop recommendation system.

                        Features:
                        - JWT Authentication
                        - Weather API
                        - AI Crop Recommendation
                        - User History
                        - Pagination
                        - Validation
                        """);

        Contact contact = new Contact()
                .name("Hardik Kaushik")
                .email("hardikkaushik070@gmail.com");

        License license = new License()
                .name("MIT License");

        info.setContact(contact);
        info.setLicense(license);

        ExternalDocumentation externalDocumentation = new ExternalDocumentation()
                .description("GitHub Repository");

        SecurityScheme securityScheme = new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        Components components = new Components()
                .addSecuritySchemes("Bearer Authentication", securityScheme);

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        return new OpenAPI()
                .info(info)
                .externalDocs(externalDocumentation)
                .components(components)
                .addSecurityItem(securityRequirement);
    }
}
