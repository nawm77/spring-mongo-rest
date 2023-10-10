package org.ilya.mongoproject.controller;

import org.ilya.mongoproject.exceptionHandler.ApiException;
import org.ilya.mongoproject.model.dto.request.RentRequestDTO;
import org.ilya.mongoproject.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

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
    public ResponseEntity<?> getAllRents(@RequestParam(name = "page", required = false) Integer page){
        try {
            return page == null ?
                    ResponseEntity.status(HttpStatus.FOUND).body(rentService.findAll())
                    :
                    ResponseEntity.status(HttpStatus.FOUND).body(rentService.findAllWithLimit(page));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewRent(@RequestBody RentRequestDTO rentRequestDTO) throws ExecutionException, InterruptedException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(rentService.addNewRent(rentRequestDTO));
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiException.builder()
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRentById(@PathVariable(name = "id") String id){
        try{
            return ResponseEntity.status(HttpStatus.FOUND).body(rentService.findRentById(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiException(e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editExistingRent(@RequestBody RentRequestDTO rentRequestDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rentService.editExistingRent(rentRequestDTO));
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiException(e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRentById(@PathVariable("id") String id){
        try{
            rentService.deleteRentById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiException(e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }
}
