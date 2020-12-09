package com.example.ClassBooking.db;

import com.example.ClassBooking.db.ClassItem;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class UserBookingDay {

    @Id
    String id;

    String email;
    String password;
    Boolean isClass;
    String date;
    ArrayList<ClassItem> classItemList;

    public UserBookingDay(String email, String password, Boolean isClass, String date){
        this.email=email;
        this.password=password;
        this.isClass=isClass;
        this.date=date;
        this.classItemList = new ArrayList<>();
    }

    public ArrayList<ClassItem> getClassItemList(){
        return this.classItemList;
    }

    public void addClassItem(ClassItem item){
        this.classItemList.add(item);
    }

    public ArrayList<ClassItem> getClassItems(){
        return this.classItemList;
    }

    public ClassItem getClassItem(int index){
        return this.classItemList.get(index);
    }



}
