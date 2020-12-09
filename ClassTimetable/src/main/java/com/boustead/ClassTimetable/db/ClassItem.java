package com.boustead.ClassTimetable.db;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.w3c.dom.html.HTMLImageElement;

import java.util.Date;

public class ClassItem {

    private boolean isClass;
    private String date;
    private String time;
    private String name;
    private String instructor;
    private String duration;
    private String bookingId;
    private int locationId;

    public ClassItem(){}

    public ClassItem(boolean isClass, String date, String time, String name, String instructor, String duration, String bookingId, int locationId){
        this.date=date;
        this.isClass = isClass;
        this.time = time;
        this.name = name;
        this.instructor = instructor;
        this.duration = duration;
        this.bookingId = bookingId;
        this.locationId = locationId;
    }

    public boolean isClass() {
        return isClass;
    }
}
