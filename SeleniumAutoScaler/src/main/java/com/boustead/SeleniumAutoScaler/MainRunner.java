package com.boustead.SeleniumAutoScaler;

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
        log.info("up cron: " + configProperties.getUpCron());
        log.info("down cron: " + configProperties.getDownCron());
        log.info("user request ip: " + configProperties.getUserRequestIp());
        log.info("user request port: " + configProperties.getUserRequestPort());
        log.info("hub ip: " + configProperties.getHubIp());
        log.info("hub port: " + configProperties.getHubPort());
        log.info("default scale: " + configProperties.getDefaultScale());

    }
}
