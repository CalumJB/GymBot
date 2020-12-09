package com.example.ClassBooking.booking;

import com.example.ClassBooking.db.UserBookingDay;
import com.example.ClassBooking.db.UserBookingDayRepository;
import com.example.ClassBooking.utility.ConfigProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Schedulers that trigger at the booking time every day to get the days booking from db
 * The days booking will be todays date + prebooking period
 */

@Component
public class Scheduler {

    Logger log = LogManager.getLogger(Scheduler.class);

    @Autowired
    UserBookingDayRepository userBookingDayRepository;

    @Autowired
    ConfigProperties configProperties;

    @Scheduled(initialDelay = 1000 * 5, fixedDelay=Long.MAX_VALUE)
    //@Scheduled(cron = "${booking.classes.time}")
    public void sendBookingRequestForClasses(){

        // Get the days that should be booked for
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, configProperties.getBookingClassesDays());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = format.format(cal.getTime());
        log.info("Getting all user requests for date " + formattedDate);


        List<UserBookingDay> listBookings = userBookingDayRepository.findAllByDateAndIsClass(formattedDate, true);

        log.info(listBookings.size());
        log.info(listBookings.toString());
        for(UserBookingDay user: listBookings){

        }


        // Send as JSON to Booking microservice



    }



}
