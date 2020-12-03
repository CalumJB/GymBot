package com.boustead.ClassTimetable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties
public class ConfigProperties {

    private String schedulerCron;
    private Map<String, Integer> location;
    //private List<String> locationList;
    private String hubIp;
    private String hubPort;
    private String adminEmail;
    private String adminPassword;

    public String getSchedulerCron() {
        return schedulerCron;
    }

    public void setSchedulerCron(String schedulerCron) {
        this.schedulerCron = schedulerCron;
    }

//    public List<String> getLocationList() {
//        return locationList;
//    }
//
//    public void setLocationList(List<String> locationList) {
//        this.locationList = locationList;
//    }

    public String getHubIp() {
        return hubIp;
    }

    public void setHubIp(String hubIp) {
        this.hubIp = hubIp;
    }

    public String getHubPort() {
        return hubPort;
    }

    public void setHubPort(String hubPort) {
        this.hubPort = hubPort;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Map<String, Integer> getLocation() {
        return location;
    }

    public void setLocation(Map<String, Integer> location) {
        this.location = location;
    }



}
