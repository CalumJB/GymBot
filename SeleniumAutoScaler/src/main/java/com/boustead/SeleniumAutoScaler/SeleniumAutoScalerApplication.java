package com.boustead.SeleniumAutoScaler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SeleniumAutoScalerApplication{

	public static void main(String[] args) {
		SpringApplication.run(SeleniumAutoScalerApplication.class, args);
	}

}
