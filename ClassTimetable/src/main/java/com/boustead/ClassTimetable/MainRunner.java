package com.boustead.ClassTimetable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainRunner implements CommandLineRunner {

    Logger log = LogManager.getLogger();

    @Autowired
    ConfigProperties configProperties;

    @Override
    public void run(String... args) throws Exception {

        log.info("Configuration Properties");
        log.info("scheduler cron: " + configProperties.getSchedulerCron());
        log.info("Location HashMap: " + configProperties.getLocation());
        log.info("Admin email: " + configProperties.getAdminEmail());
        log.info("Admin password: " + configProperties.getAdminPassword());
        //log.info("location list: " + configProperties.getLocationList());

//        for(String location: configProperties.getLocationList()){
//            log.info("Getting timetable for location: " + location);
//        }

    }
}
