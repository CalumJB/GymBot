package com.boustead.SeleniumAutoScaler;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;


@RestController
public class TestRet {

    Logger logger = LoggerFactory.getLogger(TestRet.class);

    @Value("${grid.ip}")
    private String gridIP;

    @Value("${grid.port}")
    private String gridPort;

    @Value("${grid.browser.name}")
    private String gridBrowserName;

    @Value("${grid.browser.platform}")
    private String gridBrowserPlatform;


    @GetMapping("/testCheckQueue")
    public String testCheckQueue(){



        //Check env variables are set
        logger.info("Environment variables");
        logger.info("grid.ip: " + gridIP);
        logger.info("grid.port: " + gridPort);
        logger.info("grid.browser.name: " + gridBrowserName);
        logger.info("grid.broswer.platform: " + gridBrowserPlatform);


        //Chrome options
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
        options.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        //options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.addArguments("headless");

        // Define capabilities of browser
        //DesiredCapabilities capabiltiy = DesiredCapabilities.chrome(options=chrome_options);
        //capabiltiy.setBrowserName(gridBrowserName);

        //Platform plat = Platform.fromString("LINUX");
        //logger.info("plat=" + plat.toString());

        //capabiltiy.setPlatform(Platform.LINUX);

        //Create URL from hub
        URL hubUrl;
        try {
            hubUrl = new URL("http://" + gridIP + ":" + gridPort + "/wd/hub/");
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException: " + e.getLocalizedMessage());
            return "error";
        }

        //Create driver
        WebDriver driver = new RemoteWebDriver(hubUrl, options);

        driver.get("http://google.com");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        String urla = driver.getCurrentUrl();
        String title = driver.getTitle();
        driver.quit();
        return "current url is: " + urla + "\ncurrent title is: " + title;
    }


    @GetMapping("/testChangeRep")
    public String testChangeRep(){
        return "null";
    }
}
