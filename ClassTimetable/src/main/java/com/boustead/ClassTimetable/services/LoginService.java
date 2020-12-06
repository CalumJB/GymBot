package com.boustead.ClassTimetable.services;

import com.boustead.ClassTimetable.ConfigProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

    @Autowired
    ConfigProperties configProperties;

    Logger log = LogManager.getLogger(LoginService.class);

    public Boolean login(WebDriver driver){

        //Navigate to website
        driver.get("https://gymbox.legendonlineservices.co.uk/enterprise/account/login");

        //Check if user is already logged in
        if(driver.getCurrentUrl().contains("home")){
            log.info("Already logged in, logout");
            logout(driver);
        }

        //Find the tag of email, password and login button
        WebElement emailField = driver.findElement(By.id("login_Email"));
        WebElement passwordField = driver.findElement(By.id("login_Password"));
        WebElement submitBtn = driver.findElement( By.id("login"));

        //Check valid
        if(emailField == null || passwordField == null || submitBtn == null){
            log.error("Error on login");
            log.error("Could not find element by is. emailField, passwordField or submitBtn");
            return false;
        }

        //Enter email and password
        emailField.sendKeys(configProperties.getAdminEmail());
        passwordField.sendKeys(configProperties.getAdminPassword());

        // Click submit
        submitBtn.click();

        //Check if login success
        if(driver.getCurrentUrl().contains("home")){
            log.info("Login successful");
            return true;
        }

        log.error("Error on login");
        log.error("After login, browser does not navigate to home-page. Check email and password.");
        return false;
    }

    public Boolean logout(WebDriver driver){
        return true;
    }
}
