package org.ilya.mongoproject.controller;

import org.ilya.mongoproject.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.ilya.mongoproject.model.constants.ApiConstants.BIKE_API_V1_PATH;

@RestController
@RequestMapping(BIKE_API_V1_PATH)
public class BikeController {
    private final BikeService bikeService;

    @Autowired
    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllBikes(){
        try{
            bikeService.
        }
    }
}
