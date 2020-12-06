package com.boustead.ClassTimetable.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocationClassesRepository extends MongoRepository<LocationClasses, String> {

    public List<LocationClasses> deleteAllByLocationId(int locationId);

    public LocationClasses findByLocationIdAndIsClasses(int locationId, Boolean isClasses);
}
