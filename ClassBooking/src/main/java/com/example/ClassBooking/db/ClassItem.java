package com.example.ClassBooking.db;

import java.util.Date;

public class ClassItem {

    private boolean isClass;
    private String date;
    private String bookingId;
    private int locationId;

    public ClassItem(){}

    public ClassItem(boolean isClass, String date, String bookingId, int locationId){
        this.date=date;
        this.isClass = isClass;
        this.bookingId = bookingId;
        this.locationId = locationId;
    }

    public boolean isClass() {
        return isClass;
    }
}
