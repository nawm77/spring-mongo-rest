package org.ilya.mongoproject.controller;

import org.ilya.mongoproject.exceptionHandler.ApiException;
import org.ilya.mongoproject.model.dto.request.RentRequestDTO;
import org.ilya.mongoproject.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static org.ilya.mongoproject.model.constants.ApiConstants.RENT_API_V1_PATH;

@RestController
@RequestMapping(RENT_API_V1_PATH)
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("/")
    public ResponseEntity getAllRents(@RequestParam(name = "count", required = false) Integer count){
        try {
            return count == null ? ResponseEntity.status(HttpStatus.FOUND).body(rentService.findAll()) : ResponseEntity.status(HttpStatus.FOUND).body(rentService.findAllWithLimit(count));
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PostMapping("/new")
    public ResponseEntity addNewRent(@RequestBody RentRequestDTO rentRequestDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(rentService.addNewRent(rentRequestDTO));
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiException.builder()
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build());
        }
    }
}
