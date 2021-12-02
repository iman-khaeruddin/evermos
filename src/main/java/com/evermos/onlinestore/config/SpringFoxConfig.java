package com.evermos.onlinestore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.evermos"))
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo())
                .directModelSubstitute(Timestamp.class, Long.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Evermos API Service")
                .description("Evermos API Service").version("2.0")
                .contact(new Contact("Iman K", "", "iman.khaeruddin@gmail.com"))
                .build();
    }

}