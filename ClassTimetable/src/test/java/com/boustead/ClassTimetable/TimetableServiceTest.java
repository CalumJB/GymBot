package com.boustead.ClassTimetable;

import com.boustead.ClassTimetable.services.TimetableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TimetableServiceTest {

    @Autowired
    TimetableService timetableService;

//    @Test
//    public void testDateTimeConversion(){
//        try{
//            Date dateTime = timetableService.stringDateTimeToDate("Wednesday - 02 December 2020", "08:00");
//            System.out.println(dateTime.toString());;
//        }catch (Exception e){
//            fail();
//        }

    //}

}