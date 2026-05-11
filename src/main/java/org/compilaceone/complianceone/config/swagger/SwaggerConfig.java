package org.compilaceone.complianceone.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI complianceOneOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ComplianceOne API")
                        .description("Plataforma de Governança, Compliance e Inteligência Organizacional")
                        .version("v0.0.1")
                        .contact(new Contact()
                                .name("Ivan Oliveira")
                                .email("ivanolivendev@protonmail.com"))
                        .license(new License()
                                .name("MIT")
                                .url("http://springdoc.org")));
    }
}
