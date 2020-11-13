package com.boustead.gymbotclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserSingleton {

    private static BrowserSingleton instance;
    public ChromeDriver browser;


    private BrowserSingleton() {
        //Create Selenium Browser
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");

        browser = new ChromeDriver(options);

        //browser.get("https://gymbox.legendonlineservices.co.uk/enterprise/BookingsCentre/MemberTimetable");
    }

    public static BrowserSingleton getInstance() {
        if(instance == null) {
            instance = new BrowserSingleton();
        }

        return instance;
    }

}
