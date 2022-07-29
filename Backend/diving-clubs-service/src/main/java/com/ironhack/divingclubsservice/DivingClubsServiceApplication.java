package com.ironhack.divingclubsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DivingClubsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DivingClubsServiceApplication.class, args);
	}

}
