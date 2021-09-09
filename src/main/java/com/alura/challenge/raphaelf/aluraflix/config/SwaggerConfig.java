package com.alura.challenge.raphaelf.aluraflix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.alura.challenge.raphaelf.aluraflix.controllers"))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.ant("/videos/**"))
//                .paths(PathSelectors.ant("/categorias/**"))
                .build();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:" + "/swagger-ui/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Aluraflix API",
                "Aluraflix API desenvolvido como challenge backend com a Alura",
                "API TOS",
                "Terms of service",
                new Contact("Raphael Fontoura", "www.fontouradev.com", "raphael.fontoura@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
