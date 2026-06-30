package com.hitachi.smartpark_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SmartParkServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartParkServiceApplication.class, args);
	}
}
