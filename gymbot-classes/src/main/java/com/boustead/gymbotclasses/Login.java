package com.boustead.gymbotclasses;

import org.openqa.selenium.WebElement;

public class Login {

    public static Boolean logUserIn(String email, String password){

        //Navigate to website
        BrowserSingleton.getInstance().browser.get("https://gymbox.legendonlineservices.co.uk/enterprise/account/login");

        //Check if user is already logged in
        if(BrowserSingleton.getInstance().browser.getCurrentUrl().contains("home")){
            logUserOut();
        }

        //Find the tag of email, password and login button
        WebElement emailField = BrowserSingleton.getInstance().browser.findElementById("login_Email");
        WebElement passwordField = BrowserSingleton.getInstance().browser.findElementById("login_Password");
        WebElement submitBtn = BrowserSingleton.getInstance().browser.findElementById("login");

        if(emailField == null || passwordField == null || submitBtn == null){
            return false;
        }

        //Enter email and password
        emailField.sendKeys(email);
        emailField.sendKeys(password);

        // Click submit
        submitBtn.clear();

        //Check if login success
        if(BrowserSingleton.getInstance().browser.getCurrentUrl().contains("home")){
            return true;
        }

        return false;
    }

    public static void logUserOut(){
        BrowserSingleton.getInstance().browser.get("https://gymbox.legendonlineservices.co.uk/enterprise/account/logout");
    }

}
