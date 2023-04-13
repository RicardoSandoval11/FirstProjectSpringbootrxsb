package com.springboot1.firstprojectspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.springboot1.*") 
@EntityScan("com.springboot1.*")
@EnableJpaRepositories(basePackages = "com.springboot1.repository")
public class FirstprojectspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstprojectspringbootApplication.class, args);
	}

}
