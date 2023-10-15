package org.ilya.mongoproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.ilya.mongoproject.model.constants.ApiConstants.HEALTH_API_V1_PATH;

@RestController
@RequestMapping(HEALTH_API_V1_PATH)
public class HealthCheck {
    @GetMapping("/")
    public ResponseEntity<?> getHealthStatus(){
        return ResponseEntity.ok("OK");
    }
}
