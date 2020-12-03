package com.boustead.ClassTimetable;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Component
public class TimetableService {

    Logger log = LogManager.getLogger(TimetableService.class);

    @Autowired
    ConfigProperties configProperties;

    //Loop through the list of locations and get the timetable for each
    //Uses the same driver session so not async
    public void getClassTimetables(WebDriver driver){
        for(Map.Entry<String, Integer> entry: configProperties.getLocation().entrySet()){
            log.info("Getting timetable for: " +entry.getKey());
            getTimetableForLocation(driver, entry.getValue());
        }
    }

    private void getTimetableForLocation(WebDriver driver, int locId){

        //Go to webpage
        driver.get("https://gymbox.legendonlineservices.co.uk/enterprise/BookingsCentre/MemberTimetable?clubId=" + locId);

        //Get full timetable
        WebElement memberTimetable = driver.findElement(By.id("MemberTimetable"));

        String dateString = null;
        //Loop through each row in the table
        for(WebElement tr: memberTimetable.findElements(By.cssSelector("tr"))){

            // check classname
            String classname = tr.getAttribute("class");

            //if date
            if(classname.equals("dayHeader")){ //date
                //Get date header
                log.info(tr.getText());
                dateString=tr.getText();
                if(dateString.isEmpty()){ log.error("Unable to get date for table row");}
            }

            else if(!classname.equals("header")){ //class
                //each class row contains 7 columns
                ClassItem classItem = getClassFromTableRow(dateString,tr);

            }

        }


        //Start up the hub, node and a proxy
    }

    //Attemps to convert table row (tr) into a class object
    private ClassItem getClassFromTableRow(String dateString, WebElement tr){

        //Get the web elements for a row
        String timeStr=null;
        String nameStr=null;
        String instructorStr=null;
        String durationStr=null;
        String bookingIdStr=null;

        //For each element, check it exists, get element and get text
        if(tr.findElements(By.className("col0Item")).size()!=0){ timeStr = tr.findElement(By.className("col0Item")).getText(); }
        if(tr.findElements(By.className("col1Item")).size()!=0){ nameStr = tr.findElement(By.className("col1Item")).getText(); }
        if(tr.findElements(By.className("col3Item")).size()!=0){ instructorStr = tr.findElement(By.className("col3Item")).getText(); }
        if(tr.findElements(By.className("col4Item")).size()!=0){ durationStr = tr.findElement(By.className("col4Item")).getText(); }
        if(tr.findElements(By.className("col6Item")).size()!=0){ bookingIdStr = tr.findElement(By.className("col6Item")).getAttribute("id"); }

        //Return null if important columns are missing is empty
        if(timeStr==null || nameStr==null || bookingIdStr==null
                || timeStr.isEmpty() || nameStr.isEmpty() || bookingIdStr.isEmpty()){ return null; }

        // If instructor or duration is empty set
        if(instructorStr==null || instructorStr.isEmpty()){ instructorStr=""; }
        if(durationStr==null || durationStr.isEmpty()){ durationStr=""; }

        //Now that all elements are valid and set

        //Convert date and time string to a Date object
        Date dateTime = stringDateTimeToDate(dateString, timeStr);
        if(dateTime==null){ return null; }
        return new ClassItem(dateTime, nameStr, instructorStr, bookingIdStr);



    }

    public Date stringDateTimeToDate(String date, String time){
        String dateAr[] = date.split(" - ");
        date = dateAr[1];
        String dateTimeString = time + ", " + date;
        DateFormat format = new SimpleDateFormat("HH:mm, d MMMM yyyy", Locale.ENGLISH);
        try {
            Date formattedDate = format.parse(dateTimeString);
            return formattedDate;
        } catch (ParseException e) {
            log.error("Unable to convert table date and time to Date object");
            log.error(e.getMessage());
            return null;
        }

    }

}
