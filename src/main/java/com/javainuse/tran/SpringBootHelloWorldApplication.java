package com.javainuse.tran;

import com.javainuse.controllers.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.javainuse.service", "com.javainuse.controllers", "com.javainuse.exception", "com.javainuse.org.response"})
@EntityScan({"com.javainuse.model"})
@EnableJpaRepositories({"com.javainuse.repository"})
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class SpringBootHelloWorldApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringBootHelloWorldApplication.class, args);
    }
}
