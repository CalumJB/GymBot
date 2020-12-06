package com.boustead.ClassTimetable.services;


import com.boustead.ClassTimetable.ConfigProperties;
import com.boustead.ClassTimetable.db.ClassItem;
import com.boustead.ClassTimetable.db.LocationClasses;
import com.boustead.ClassTimetable.db.LocationClassesRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


    @Autowired
    LocationClassesRepository locationClassesRepository;

    //Loop through the list of locations and get the timetable for each
    //Uses the same driver session so not async
    public void getClassTimetables(WebDriver driver){
        for(Map.Entry<String, Integer> entry: configProperties.getLocation().entrySet()){
            log.info("Getting timetable for: " + entry.getKey());
            getTimetableForLocation(driver, entry.getKey(), entry.getValue());
        }
    }

    private void getTimetableForLocation(WebDriver driver, String locName, int locId){

        LocationClasses classLocationClasses = new LocationClasses(true, locName, locId);
        LocationClasses gymBookingLocationClasses = new LocationClasses(false, locName, locId);

        //Go to webpage
        driver.get("https://gymbox.legendonlineservices.co.uk/enterprise/BookingsCentre/MemberTimetable?clubId=" + locId);

        //Check that timetable exists and return if not
        if(driver.findElements(By.id("MemberTimetable")).size()==0){ return; }

        //Get full timetable
        WebElement memberTimetable = driver.findElement(By.id("MemberTimetable"));

        String dateString = null;
        //Loop through each row in the table
        for(WebElement tr: memberTimetable.findElements(By.cssSelector("tr"))){

            // check classname
            String classname = tr.getAttribute("class");

            if(classname.equals("dayHeader")){ //date
                //Get date header
                dateString=tr.getText();
                if(dateString.isEmpty()){ log.error("Unable to get date for table row");}
            }
            else if(!classname.equals("header")){ //class

                //Create class item from tr elements and date
                ClassItem classItem = getClassFromTableRow(dateString,tr);

                //Skip to next row if class item not created
                if (classItem==null){ continue; }

                //Add to correct item
                if(classItem.isClass()){
                    //add to class array
                    classLocationClasses.addItem(classItem);
                }else{
                    //add to booking array
                    gymBookingLocationClasses.addItem(classItem);
                }

            }
        }

        locationClassesRepository.deleteAllByLocationId(locId);
        locationClassesRepository.save(classLocationClasses);
        locationClassesRepository.save(gymBookingLocationClasses);
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

        //determine whether class of gym booking
        boolean isClass;

        if(nameStr.equals("Gym Time") || nameStr.equals("Gym Entry Time") || nameStr.equals("Last Entry Time")){ isClass=false; }
        else{ isClass=true; }

        return new ClassItem(isClass, dateTime, nameStr, instructorStr, bookingIdStr);

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
