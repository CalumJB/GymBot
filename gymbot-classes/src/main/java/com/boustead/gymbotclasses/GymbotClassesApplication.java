package com.boustead.gymbotclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class GymbotClassesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymbotClassesApplication.class, args);

		BrowserSingleton.getInstance();

	}

}
