package com.boustead.SeleniumAutoScaler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class ConfigProperties {

    private String upCron;
    private String downCron;
    private String userRequestIp;
    private String userRequestPort;
    private String hubIp;
    private String hubPort;
    private String deployment;
    private String namespace;
    private int defaultScale;
    private int maxScale;



    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }


    public String getUserRequestIp() {
        return userRequestIp;
    }

    public void setUserRequestIp(String userRequestIp) {
        this.userRequestIp = userRequestIp;
    }

    public String getUserRequestPort() {
        return userRequestPort;
    }

    public void setUserRequestPort(String userRequestPort) {
        this.userRequestPort = userRequestPort;
    }


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

    public int getDefaultScale() {
        return defaultScale;
    }

    public void setDefaultScale(String defaultScale) {
        this.defaultScale = Integer.parseInt(defaultScale);
    }

    public String getDeployment() {
        return deployment;
    }

    public void setDeployment(String deployment) {
        this.deployment = deployment;
    }

    public String getUpCron() {
        return upCron;
    }

    public void setUpCron(String upCron) {
        this.upCron = upCron;
    }

    public String getDownCron() {
        return downCron;
    }

    public void setDownCron(String downCron) {
        this.downCron = downCron;
    }

    public int getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(String maxScale) {
        this.maxScale = Integer.parseInt(maxScale);
    }
}
