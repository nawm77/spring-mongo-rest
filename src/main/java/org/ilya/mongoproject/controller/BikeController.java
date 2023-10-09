package org.ilya.mongoproject.controller;

import org.ilya.mongoproject.exceptionHandler.ApiException;
import org.ilya.mongoproject.model.dto.request.BikeRequestDTO;
import org.ilya.mongoproject.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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
    public ResponseEntity<?> getAllBikes(@RequestParam(name = "page", required = false) Integer page){
        try{
            return page == null ?
                    ResponseEntity.status(HttpStatus.FOUND).body(bikeService.findAll())
                    :
                    ResponseEntity.status(HttpStatus.FOUND).body(bikeService.findAllWithLimit(page));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBikeById(@PathVariable(name = "id") String id){
        try{
            return ResponseEntity.status(HttpStatus.FOUND).body(bikeService.findById(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiException(e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewBike(@RequestBody BikeRequestDTO bikeRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(bikeService.addNewBike(bikeRequestDTO));
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editExistingBike(@RequestBody BikeRequestDTO bikeRequestDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bikeService.editExistingBike(bikeRequestDTO));
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiException(e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }
}
