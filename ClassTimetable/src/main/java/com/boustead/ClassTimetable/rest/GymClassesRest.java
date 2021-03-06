package com.boustead.ClassTimetable.rest;

import com.boustead.ClassTimetable.db.LocationClasses;
import com.boustead.ClassTimetable.db.LocationClassesRepository;
import com.google.gson.Gson;
import org.apache.logging.log4j.core.pattern.MethodLocationPatternConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class GymClassesRest {

    @Autowired
    LocationClassesRepository locationClassesRepository;

    @GetMapping(path = "/timetable", produces= MediaType.APPLICATION_JSON_VALUE)
    public String getTimeTable(HttpServletResponse response, @RequestParam(defaultValue = "default") String locationId, @RequestParam(defaultValue = "default") String isClasses){

        response.addHeader("Access-Control-Allow-Origin", "*");

        //Check values provided
        if(locationId.equals("default") || isClasses.equals("default")){ return "{\"Message\":\"Parameters missing\"}";  }

        //Convert to correct type
        int locationIdInt = Integer.parseInt(locationId);
        boolean isClassesBool = Boolean.parseBoolean(isClasses);

        //Get timetable from db
        LocationClasses timetable = getTimetableFromDb(locationIdInt, isClassesBool);

        if(timetable==null){ return "{\"Message\":\"No timetable found\"}";}

            //Convert timetable to json
        return new Gson().toJson(timetable.getClassItemList());

    }

    private LocationClasses getTimetableFromDb(int locId, boolean isClass){

        return locationClassesRepository.findByLocationIdAndIsClasses(locId, isClass);

    }


}
