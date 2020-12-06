package com.boustead.ClassTimetable.services;

import com.boustead.ClassTimetable.ConfigProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Component
public class WebDriverService {

    Logger log = LogManager.getLogger(WebDriverService.class);

    @Autowired
    ConfigProperties configProperties;

    //Returns a configured RemoteWebDriver
    public WebDriver getWebDriver(){

        log.info("Creating web driver");

        //Chrome options
        ChromeOptions options = new ChromeOptions();
        //options.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.MAC);
        options.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);

        options.setHeadless(true);

        //Create driver
        try{
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
            WebDriver driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
            //WebDriver driver = new RemoteWebDriver(getHubUrl(), options);
            log.info("WebDriver created");
            return driver;
        }catch(Exception e){
            log.error("Unable to create remote web driver");
            log.error(e.getMessage());
            return null;
        }

    }

    private URL getHubUrl(){
        try {
            return new URL("http://" + configProperties.getHubIp() + ":" + configProperties.getHubPort() + "/wd/hub/");
        } catch (MalformedURLException e) {
            log.error("Unable to create URL for Hub");
            log.error(e.getMessage());
            return null;
        }
    }

}
