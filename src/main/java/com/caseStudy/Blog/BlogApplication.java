package com.caseStudy.Blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.Collections;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BlogApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8082"));
        app.run(args);
    }

}
