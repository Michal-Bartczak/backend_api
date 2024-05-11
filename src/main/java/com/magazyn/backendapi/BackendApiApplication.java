package com.magazyn.backendapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BackendApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BackendApiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BackendApiApplication.class);
    }
}
