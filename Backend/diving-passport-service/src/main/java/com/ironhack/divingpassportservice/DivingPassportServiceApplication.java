package com.ironhack.divingpassportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DivingPassportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DivingPassportServiceApplication.class, args);
	}

}
