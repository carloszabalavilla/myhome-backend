package com.czabala.myhome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.czabala.myhome.domain.repository")
public class MyhomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyhomeApplication.class, args);
    }

    /*
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("*").allowedOrigins("http://localhost:3000");
            }
        };
    }
     */
}
