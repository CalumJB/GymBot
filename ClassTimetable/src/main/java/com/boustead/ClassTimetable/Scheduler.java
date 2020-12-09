package com.boustead.ClassTimetable;

import com.boustead.ClassTimetable.services.LoginService;
import com.boustead.ClassTimetable.services.TimetableService;
import com.boustead.ClassTimetable.services.WebDriverService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    //@Scheduled(cron = "${scheduled.cron.class.scrape}")
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
        log.info("Driver quit");
        driver.quit();

    }


}
