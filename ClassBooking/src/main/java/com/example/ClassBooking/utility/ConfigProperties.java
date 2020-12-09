package com.example.ClassBooking.utility;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ConfigProperties {

    private String seedValue;
    private int bookingClassesDays;
    private String bookingClassesTime;
    private int bookingGymDays;
    private String bookingGymTime;

    public int getBookingClassesDays() {
        return bookingClassesDays;
    }

    public void setBookingClassesDays(int bookingClassesDays) {
        this.bookingClassesDays = bookingClassesDays;
    }

    public String getBookingClassesTime() {
        return bookingClassesTime;
    }

    public void setBookingClassesTime(String bookingClassesTime) {
        this.bookingClassesTime = bookingClassesTime;
    }

    public int getBookingGymDays() {
        return bookingGymDays;
    }

    public void setBookingGymDays(int bookingGymDays) {
        this.bookingGymDays = bookingGymDays;
    }

    public String getBookingGymTime() {
        return bookingGymTime;
    }

    public void setBookingGymTime(String bookingGymTime) {
        this.bookingGymTime = bookingGymTime;
    }

    public String getSeedValue() {
        return seedValue;
    }

    public void setSeedValue(String seedValue) {
        this.seedValue = seedValue;
    }



}
