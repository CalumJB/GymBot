package com.boustead.ClassTimetable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.w3c.dom.html.HTMLImageElement;

import java.util.Date;

public class ClassItem {

    private Date dateTime;
    private String name;
    private String instructor;
    private String bookingId;

    public ClassItem(){}
    public ClassItem(Date dateTime, String name, String instructor, String bookingId){
        this.dateTime = dateTime;
        this.name = name;
        this.instructor = instructor;
        this.bookingId = bookingId;
    }
}
