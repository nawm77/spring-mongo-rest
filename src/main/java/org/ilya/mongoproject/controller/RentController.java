package org.ilya.mongoproject.controller;

import org.ilya.mongoproject.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.ilya.mongoproject.model.constants.ApiConstants.*;

@RestController
@RequestMapping(RENT_API_V1_PATH)
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("/")
    public ResponseEntity getAllRents(){
        return ResponseEntity.ok(rentService.findAll());
    }
}
