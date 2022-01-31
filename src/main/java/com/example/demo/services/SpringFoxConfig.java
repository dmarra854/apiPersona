package com.example.demo.services;
import com.example.demo.controllers.PersonaController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


    @Configuration
    @EnableSwagger2
    public class SpringFoxConfig {
        //private static final String REGEX_ENDPOINTS = "^/(api/\\d/.*";
        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.example.demo.controllers"))
                    .paths(PathSelectors.any())
                    .build();
        }

        private ApiInfo apiInfo() {
            Contact contact = new Contact("Profile","https://www.profile.com", "dario.marranti@gmail.com");
            return new ApiInfoBuilder()
                    .title("API Persona")
                    .description("Challenge Banco Hipotecario")
                    .version("1.0")
                    .termsOfServiceUrl(null)
                    .contact(contact)
                    .build();

        }
    }