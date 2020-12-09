package com.example.ClassBooking.db;

import com.example.ClassBooking.db.UserBookingDay;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserBookingDayRepository extends MongoRepository<UserBookingDay, String> {

    public UserBookingDay findByEmailAndIsClassAndDate(String email, boolean isClass, String date);

    public List<UserBookingDay> findAllByDateAndIsClass(String date, boolean isClass);
}
