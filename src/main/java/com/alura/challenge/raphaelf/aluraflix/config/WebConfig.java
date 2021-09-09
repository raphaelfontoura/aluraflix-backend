package com.alura.challenge.raphaelf.aluraflix.config;

import com.alura.challenge.raphaelf.aluraflix.interceptor.InterceptorToAccess;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000","http://localhost:8080");
    }

    @Bean
    public InterceptorToAccess interceptorToAccess() {
        return new InterceptorToAccess();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(interceptorToAccess()).addPathPatterns("/videos/**","/categorias/**");
    }

}
