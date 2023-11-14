package com.springboot.curbside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SpringbootCurbsideRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCurbsideRestApiApplication.class, args);
	}

}
