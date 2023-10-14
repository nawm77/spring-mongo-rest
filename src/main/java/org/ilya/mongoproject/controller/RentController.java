package org.ilya.mongoproject.controller;

import org.ilya.mongoproject.exceptionHandler.ApiException;
import org.ilya.mongoproject.mapper.RentMapper;
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
    private final RentMapper rentMapper;
    @Autowired
    public RentController(RentService rentService, RentMapper rentMapper) {
        this.rentService = rentService;
        this.rentMapper = rentMapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllRents(@RequestParam(name = "page", required = false) Integer page){
        try {
            return page == null ?
                    ResponseEntity.status(HttpStatus.FOUND).body(
                            rentService.findAll().stream()
                            .map(rentMapper::toDTO)
                            .toList()
                    )
                    :
                    ResponseEntity.status(HttpStatus.FOUND).body(
                            rentService.findAllWithLimit(page).stream()
                            .map(rentMapper::toDTO)
                            .toList()
                    );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getLocalizedMessage())
                    .build()
            );
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewRent(@RequestBody RentRequestDTO rentRequestDTO) throws ExecutionException, InterruptedException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    rentMapper.toDTO(rentService.addNewRent(rentRequestDTO))
            );
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiException.builder()
                    .message(e.getLocalizedMessage())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRentById(@PathVariable(name = "id") String id){
        try{
            return ResponseEntity.status(HttpStatus.FOUND).body(
                    rentMapper.toDTO(rentService.findRentById(id))
            );
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                            .message(e.getLocalizedMessage())
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .build()
            );
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editExistingRent(@RequestBody RentRequestDTO rentRequestDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(
                    rentMapper.toDTO(rentService.editExistingRent(rentRequestDTO))
            );
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                            .message(e.getLocalizedMessage())
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .build()
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRentById(@PathVariable("id") String id){
        try{
            rentService.deleteRentById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                            .message(e.getLocalizedMessage())
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .build()
            );
        }
    }
}
