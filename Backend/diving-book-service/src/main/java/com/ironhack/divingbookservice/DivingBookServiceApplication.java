package com.ironhack.divingbookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DivingBookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DivingBookServiceApplication.class, args);
	}

}
