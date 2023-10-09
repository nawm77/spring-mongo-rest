package org.ilya.mongoproject.repository;

import org.ilya.mongoproject.model.entities.Bike;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BikeRepository extends MongoRepository<Bike, String> {
    Optional<Bike> findBikeById(String id);
}
