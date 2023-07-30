package com.restaurant.app.controllers;

import com.restaurant.app.entities.Rating;
import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.exceptions.UserNotFoundException;
import com.restaurant.app.requests.RatingCreateRequest;
import com.restaurant.app.requests.RatingUpdateRequest;
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

    @GetMapping
    public List<RatingResponse> getAllRatings(@RequestParam Optional<Long> userId,
                                              @RequestParam Optional<Long> commentId){
        return ratingService.getAllRatings(userId,commentId);
    }

    @PostMapping
    public ResponseEntity<Void> createRating(@RequestBody RatingCreateRequest newRating) {
        Rating rating = ratingService.createOneRating(newRating);
        if(rating != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{ratingId}")
    public Rating getOneRating(@PathVariable Long ratingId) {
        Rating rating = ratingService.getOneRatingById(ratingId);
        if(rating == null) {
            throw new UserNotFoundException();
        }
        return rating;
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<User> updateOneRating(@PathVariable Long ratingId, @RequestBody RatingUpdateRequest newRating) {
        Rating rating = ratingService.updateOneRating(ratingId, newRating);
        if(rating != null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/{ratingId}")
    public void deleteOneRating(@PathVariable Long ratingId) {
        ratingService.deleteOneRating(ratingId);
    }
}
