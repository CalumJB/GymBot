package com.boustead.ClassTimetable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
public class ClassTimetableApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassTimetableApplication.class, args);
	}

}
