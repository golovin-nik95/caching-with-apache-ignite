package com.griddynamics.ngolovin.cwai;

import org.apache.ignite.springdata20.repository.config.EnableIgniteRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableIgniteRepositories
@EnableTransactionManagement
public class CachingWithApacheIgniteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachingWithApacheIgniteApplication.class, args);
    }
}
