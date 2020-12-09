package com.example.ClassBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ClassBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassBookingApplication.class, args);
	}

}
