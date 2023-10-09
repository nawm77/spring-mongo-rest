package org.ilya.mongoproject.repository;

import org.ilya.mongoproject.model.entities.Rent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends MongoRepository<Rent, String> {
    List<Rent> findAll();
    Optional<Rent> findRentById(String id);
}
