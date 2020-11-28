package com.boustead.SeleniumAutoScaler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.URI;
import java.net.http.HttpRequest;

@Component
public class Scheduler {

    Logger log = LogManager.getLogger();

    @Autowired
    ConfigProperties configProperties;

    @Autowired
    ScaleService scaleService;

    @Autowired
    HTTPRequests httpRequests;


    /*
    upscale
    Check for number of user booking requests
    Scales up selenium nodes to meet demand
     */
    @Scheduled(cron = "${scheduler.up.cron}")
    public void upscale(){

        log.info("AutoScaler Scheduler has started. Upscale");

        // Check number of user requests to be processed
       int scaleValue = httpRequests.requestNumberOfUserBookings();

        // Perform scaling
        scaleService.scaleDeployment(scaleValue);

    }

    /*
    downscale
    Checks if there are any requests left in hub queue
    Scales down a few minutes after queue=0
    Note that we don't scale down as soon as queue=0 because nodes might be still processing requests
     */
    @Scheduled(cron = "${scheduler.down.cron}")
    public void downscale(){

        log.info("AutoScaler Scheduler has started. Downscale");

        //Check queue
        httpRequests.checkHubQueueAndWait();
        scaleService.scaleDeployment(0);

    }
}
