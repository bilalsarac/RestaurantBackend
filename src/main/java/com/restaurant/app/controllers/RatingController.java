package com.restaurant.app.controllers;

import com.restaurant.app.entities.Rating;
import com.restaurant.app.exceptions.UserNotFoundException;
import com.restaurant.app.requests.RatingCreateRequest;
import com.restaurant.app.requests.RatingUpdateRequest;
import com.restaurant.app.response.AverageResponse;
import com.restaurant.app.response.RatingResponse;
import com.restaurant.app.services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    //ok
    @GetMapping
    public List<RatingResponse> getAllRatings(@RequestParam Optional<Long> userId,
                                              @RequestParam Optional<Long> restaurantId){
        return ratingService.getAllRatings(userId,restaurantId);
    }
    //ok
    @PostMapping
    public ResponseEntity<?> createRating(@RequestBody RatingCreateRequest newRating) {
        Rating rating = ratingService.createOneRating(newRating);
        if(rating != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //ok
    @GetMapping("/{ratingId}")
    public RatingResponse getOneRating(@PathVariable Long ratingId) {
        RatingResponse rating = ratingService.getOneRatingById(ratingId);
        if(rating == null) {
            throw new UserNotFoundException();
        }
        return rating;
    }
    //ok
    @PutMapping()
    public ResponseEntity<Rating> updateOneRating(@RequestParam Long userId,@RequestParam Long restaurantId, @RequestBody RatingUpdateRequest newRating) {
        Rating rating = ratingService.updateOneRating(userId,restaurantId, newRating);
        if(rating != null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
    //ok
    @DeleteMapping()
    public void deleteOneRating(@RequestParam Long userId,@RequestParam Long restaurantId) {
        ratingService.deleteOneRating(userId,restaurantId);

    }
    //ok
    @GetMapping("/rating")
    public RatingResponse getOneCommentByRestaurantAndUserIds(@RequestParam Long userId, @RequestParam Long restaurantId) {
        return ratingService.getOneRatingByRestaurantAndUserIds(userId,restaurantId);
    }
    //ok
    @GetMapping("/averageratings")
    public AverageResponse getRestaurantsAveragePoints(@RequestParam Long restaurantId){
        return ratingService.getRestaurantsAveragePoints(restaurantId);

    }

}