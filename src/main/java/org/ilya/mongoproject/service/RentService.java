package org.ilya.mongoproject.service;

import org.ilya.mongoproject.model.entities.Rent;

import java.util.List;

public interface RentService {
    List<Rent> findAll();
}
