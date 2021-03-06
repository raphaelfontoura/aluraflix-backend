package com.alura.challenge.raphaelf.aluraflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@SpringBootApplication
@EnableSwagger2
public class AluraflixApplication {

    public static void main(String[] args) {
        SpringApplication.run(AluraflixApplication.class, args);
    }

}
