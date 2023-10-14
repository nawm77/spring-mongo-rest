package org.ilya.mongoproject.controller;

import org.ilya.mongoproject.exceptionHandler.ApiException;
import org.ilya.mongoproject.mapper.BikeMapper;
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
    private final BikeMapper bikeMapper;

    @Autowired
    public BikeController(BikeService bikeService, BikeMapper bikeMapper) {
        this.bikeService = bikeService;
        this.bikeMapper = bikeMapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllBikes(@RequestParam(name = "page", required = false) Integer page,
                                         @RequestParam(name = "name", required = false) String name){
        try{
            return page == null ?
                    ResponseEntity.status(HttpStatus.FOUND).body(
                            bikeService.findAll().stream()
                                    .map(bikeMapper::toDTO)
                                    .toList()
                    )
                    :
                    ResponseEntity.status(HttpStatus.FOUND).body(
                            bikeService.findAllWithLimit(page).stream()
                                    .map(bikeMapper::toDTO)
                                    .toList()
                    );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiException.builder()
                            .message(e.getMessage())
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBikeById(@PathVariable(name = "id") String id){
        try{
            return ResponseEntity.status(HttpStatus.FOUND).body(
                    bikeService.findById(id)
            );
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewBike(@RequestBody BikeRequestDTO bikeRequestDTO){
        //todo check existing bikes
        return ResponseEntity.status(HttpStatus.CREATED).body(
                bikeMapper.toDTO(bikeService.addNewBike(bikeRequestDTO))
        );
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editExistingBike(@RequestBody BikeRequestDTO bikeRequestDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(
                    bikeService.editExistingBike(bikeRequestDTO)
            );
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBikeById(@PathVariable("id") String id){
        try{
            bikeService.deleteExistingBike(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message(e.getMessage())
                    .build()
            );
        }
    }
}
