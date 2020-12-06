package com.boustead.ClassTimetable.db;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class LocationClasses {

    @Id
    public String id;

    Boolean isClasses;
    String location;
    int locationId;
    ArrayList<ClassItem> classItemList;

    public LocationClasses(Boolean isClasses, String location, int locationId){
        this.isClasses = isClasses;
        this.location = location;
        this.locationId = locationId;
        this.classItemList = new ArrayList<>();
    }

    public ArrayList<ClassItem> getClassItemList() {
        return classItemList;
    }

    public void setClassItemList(ArrayList<ClassItem> classItemList) {
        this.classItemList = classItemList;
    }

    public void addItem(ClassItem classItem){
        this.classItemList.add(classItem);
    }

    public ClassItem getItem(int index){
        return this.classItemList.get(index);
    }


}
