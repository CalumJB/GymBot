package com.boustead.ClassTimetable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class Scheduler {

    @Autowired
    ConfigProperties configProperties;

    @Autowired
    LoginService loginService;

    @Autowired
    TimetableService timetableService;

    @Autowired
    WebDriverService webDriverService;

    Logger log = LogManager.getLogger(Scheduled.class);

    @Scheduled(initialDelay = 1000 * 30, fixedDelay=Long.MAX_VALUE)
    public void setClassTimetable(){

        log.info("Schedule started");

        //Create driver
        WebDriver driver = webDriverService.getWebDriver();
        if(driver==null){
            driver.quit();
            return;
        }

        //Try login
        Boolean loginSuccess = loginService.login(driver);
        if(loginSuccess==false){
            driver.quit();
            return;
        }

        //Get location as json and add to database
        timetableService.getClassTimetables(driver);

        //Try logout
        //loginService.logout();
        driver.quit();

    }


}
