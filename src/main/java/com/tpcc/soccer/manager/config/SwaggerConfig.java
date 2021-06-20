package com.tpcc.soccer.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.tpcc.soccer.manager.controller";

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        springfox.documentation.service.Contact contact = new Contact("Decada Team", "", "");
        ApiInfo apiInfo = new ApiInfo(
                "EMA Dashboard Service APIs",
                "EMA dashboard service apis generated by swagger2",
                "0.0.1",
                "null",
                contact,
                "",
                ""
        );

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .build()
                .apiInfo(apiInfo)
                .useDefaultResponseMessages(true)
                .genericModelSubstitutes(Optional.class);
    }
}
