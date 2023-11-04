package org.ilya.mongoproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.ilya.mongoproject.exceptionHandler.ApiException;
import org.ilya.mongoproject.mapper.BikeMapper;
import org.ilya.mongoproject.model.dto.request.BikeRequestDTO;
import org.ilya.mongoproject.model.exception.FilterArgsException;
import org.ilya.mongoproject.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static org.ilya.mongoproject.model.constants.ApiConstants.BIKE_API_V1_PATH;

@RestController
@RequestMapping(BIKE_API_V1_PATH)
@Slf4j
public class BikeController {
    private final BikeService bikeService;
    private BikeMapper bikeMapper;

    @Autowired
    public void setBikeMapper(BikeMapper bikeMapper) {
        this.bikeMapper = bikeMapper;
    }

    @Autowired
    public BikeController(BikeService bikeService, BikeMapper bikeMapper) {
        this.bikeService = bikeService;
        this.bikeMapper = bikeMapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllBikes(@RequestParam(name = "page", required = false) Integer page){
        try{
            return page == null ?
                    ResponseEntity.status(HttpStatus.FOUND).body(
                            bikeService.findAll().stream()
                                    .map(bikeMapper::toDTO)
                                    .toList())
                    :
                    ResponseEntity.status(HttpStatus.FOUND).body(
                            bikeService.findAllWithLimit(page).stream()
                                    .map(bikeMapper::toDTO)
                                    .toList());
        } catch (Exception e){
            log.warn("Exception: " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiException.builder()
                            .message(e.getLocalizedMessage())
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
            log.warn("Exception: " + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .message(e.getLocalizedMessage())
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
                    bikeService.editExistingBike(bikeMapper.toBike(bikeRequestDTO))
            );
        } catch (NoSuchElementException e){
            log.warn("Exception: " + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .message(e.getLocalizedMessage())
                            .build()
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBikeById(@PathVariable("id") String id){
        try{
            bikeService.deleteExistingBikeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e){
            log.warn("Exception: " + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message(e.getLocalizedMessage())
                    .build()
            );
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getBikeListWithPriceFilter(@RequestParam(name = "startPrice", required = false) Integer startPrice,
                                                        @RequestParam(name = "endPrice", required = false) Integer endPrice){
        try {
            return startPrice == null || endPrice == null ?
                    ResponseEntity.status(HttpStatus.FOUND).body(
                            bikeService.findAll().stream().map(bikeMapper::toDTO).toList())
                    :
                    ResponseEntity.status(HttpStatus.FOUND).body(
                            bikeService.findBikeByPriceLimitAndSortDesc(startPrice, endPrice));
        } catch (FilterArgsException e){
            log.warn("Exception: " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiException.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .message(e.getLocalizedMessage())
            );
        }
    }
}
