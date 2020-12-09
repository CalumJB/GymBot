package com.example.ClassBooking.rest;

import com.example.ClassBooking.utility.ConfigProperties;
import com.example.ClassBooking.utility.Encryption;
import com.example.ClassBooking.db.ClassItem;
import com.example.ClassBooking.db.UserBookingDay;
import com.example.ClassBooking.db.UserBookingDayRepository;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class BookingRest {



    @Autowired
    UserBookingDayRepository userBookingDayRepository;

    @Autowired
    ConfigProperties configProperties;

    /**
     * Rest endpoint that accepts requests to adding bookings to datebase
     */

    @PostMapping("/booking")
    public String addBookingToDb(HttpServletResponse response,
                                 @RequestParam(defaultValue = "default") String email,
                                 @RequestParam(defaultValue = "default") String password,
                                 @RequestParam(defaultValue = "default") String date,
                                 @RequestParam(defaultValue = "default") String isClass,
                                 @RequestParam(defaultValue = "default") String locationId,
                                 @RequestParam(defaultValue = "default") String bookingId
                                 ){
        response.addHeader("Access-Control-Allow-Origin", "*");
        //check valid
        if(email.equals("default") || password.equals("default") || date.equals("default") ||
                isClass.equals("default") || locationId.equals("default") || bookingId.equals("default") ){
            return "{\"Message\":\"Parameters missing\"}";  }

        //convert to format
        boolean isClassBool =Boolean.valueOf(isClass);
        int locationIdInt = Integer.parseInt(locationId);

        String encryptedPassword = Encryption.encryptPassword(password, configProperties.getSeedValue());

        //add to datebase
        addToDB(email, encryptedPassword, date, isClassBool,locationIdInt , bookingId);

        return "done";
    }

    /**
     * Rest endpoint that accepts Get requests and returns class bookings for the required day
     */
    @GetMapping(path = "/classBookings", produces= MediaType.APPLICATION_JSON_VALUE)
    public String getClassBookingForDay(HttpServletResponse response, @RequestParam(defaultValue = "default") String day){
        response.addHeader("Access-Control-Allow-Origin", "*");
        if(day.equals("default")){ return "{\"Message\":\"Parameters missing\"}";  }
        List<UserBookingDay> listBookings = userBookingDayRepository.findAllByDateAndIsClass(day, true);
        if(listBookings.size()==0){ return "{\"Message\":\"No bookings\"}";  }
        return new Gson().toJson(listBookings);
    }

    /**
     * Rest endpoint that accepts Get requests and returns gym bookings for the required day
     */
    @GetMapping(path = "/gymBookings", produces= MediaType.APPLICATION_JSON_VALUE)
    public String getGymBookingsForDay(HttpServletResponse response, @RequestParam(defaultValue = "default") String day){
        response.addHeader("Access-Control-Allow-Origin", "*");
        if(day.equals("default")){ return "{\"Message\":\"Parameters missing\"}";  }
        List<UserBookingDay> listBookings = userBookingDayRepository.findAllByDateAndIsClass(day, false);
        if(listBookings.size()==0){ return "{\"Message\":\"No bookings\"}";  }
        return new Gson().toJson(listBookings);
    }


    private void addToDB(String email, String password, String date, boolean isClass, int locationId, String bookingId){

        //Create class for new booking
        ClassItem classItem = new ClassItem(isClass, date, bookingId, locationId);

        //check whether db entry for user or date already exists
        UserBookingDay userBooking = userBookingDayRepository.findByEmailAndIsClassAndDate(email, isClass, date);

        if(userBooking!=null){ //usr booking does exist to update
            userBooking.addClassItem(classItem);
            userBookingDayRepository.save(userBooking);
        }
        else{ //user booking does not exist so create new
            UserBookingDay userBookingDay = new UserBookingDay(email, password, isClass, date);
            userBookingDay.addClassItem(classItem);
            userBookingDayRepository.save(userBookingDay);
        }

    }



}
