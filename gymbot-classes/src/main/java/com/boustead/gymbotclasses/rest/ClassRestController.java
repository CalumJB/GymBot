package com.boustead.gymbotclasses.rest;

import com.boustead.gymbotclasses.BrowserSingleton;
import com.boustead.gymbotclasses.Login;
import com.boustead.gymbotclasses.Timetable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassRestController {

    @GetMapping("/timetable")
    public String greeting(@RequestParam(value = "location", defaultValue = "null") String location,
                           @RequestParam(value = "email", defaultValue = "null") String email,
                           @RequestParam(value = "password", defaultValue = "null") String password) {


        BrowserSingleton.getInstance();
            return "test";
//        if(location.equals("null") || email.equals("null") || password.equals("null")){
//                return "missing params";
//        }
//
//        Timetable table = new Timetable();
//
//
//        Boolean res = Login.logUserIn(email, password);
//
//        if(res==true){
//            return "true";
//        }else{
//            return "false";
//        }
    }
}
