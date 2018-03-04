package com.companyName.validationService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket estPricingTierApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("validation-service")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .paths(regex("/validate.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("validation-service")
                .description("This service is leveraged for validation of password and others.")
                .license("Company Name Internal Use Only")
                .licenseUrl("http://www.companyName.com copyright")
                .termsOfServiceUrl("http://terms of service")
                .version("0.1")
                .contact(new Contact("teamName@companyName.com", "", ""))
                .build();
    }
}