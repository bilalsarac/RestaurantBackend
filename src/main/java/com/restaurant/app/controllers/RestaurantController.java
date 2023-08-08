package com.restaurant.app.controllers;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.entities.Rating;
import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.requests.RestaurantCreateRequest;
import com.restaurant.app.requests.RestaurantUpdateRequest;
import com.restaurant.app.response.CommentResponse;
import com.restaurant.app.response.RatingResponse;
import com.restaurant.app.response.RestaurantResponse;
import com.restaurant.app.services.CommentService;
import com.restaurant.app.services.RatingService;
import com.restaurant.app.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;
    private RatingService ratingService;
    private CommentService commentService;

    public RestaurantController(RestaurantService restaurantService, CommentService commentService,RatingService ratingService) {
        this.restaurantService = restaurantService;
        this.ratingService = ratingService;
        this.commentService = commentService;
    }
    //ok
    @GetMapping
    public List<RestaurantResponse> getAllRestaurants(){
        return restaurantService.getAllRestaurants();
    }
    //ok
    @GetMapping("/{restaurantId}")
    public RestaurantResponse getOneRestaurant(@PathVariable Long restaurantId) {
        RestaurantResponse restaurant = restaurantService.getOneRestaurantForResponse(restaurantId);
        return restaurant;
    }
    //ok
    @PostMapping
    @PreAuthorize("hasRole('senior')")
    public ResponseEntity<Void> createRestaurant(@RequestBody RestaurantCreateRequest newRestaurant) {
        Restaurant restaurant = restaurantService.saveOneRestaurant(newRestaurant);
        if(restaurant != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //ok
    @PutMapping("/{restaurantId}")
    @PreAuthorize("hasRole('senior')")
    public ResponseEntity<User> updateOneRestaurant(@PathVariable Long restaurantId, @RequestBody RestaurantUpdateRequest newRestaurant) {
        Restaurant restaurant = restaurantService.updateOneRestaurant(restaurantId, newRestaurant);
        if(restaurant != null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
    //ok
    @DeleteMapping("/{restaurantId}")
    @PreAuthorize("hasRole('admin')")
    public void deleteOneRestaurant(@PathVariable Long restaurantId) {
        restaurantService.deleteOneRestaurant(restaurantId);
    }
    //ok
    @GetMapping("/{userId}/restaurants")
    public List<RestaurantResponse> getRestaurantsByUserId(@PathVariable Long userId){
        return restaurantService.getRestaurantsByUserId(userId);
    }
    @GetMapping("/search")
    public List<RestaurantResponse> searchRestaurants(@RequestParam("keyword") String keyword) {
        List<Restaurant> restaurants = restaurantService.searchRestaurants(keyword);

        List<RestaurantResponse> restaurantResponses = restaurants.stream()
                .map(r -> {
                    List<RatingResponse> ratingResponses = ratingService.getAllRatings(Optional.ofNullable(null),Optional.of(r.getId()));
                    List<CommentResponse> commentResponses = commentService.getAllCommentsWithParam(Optional.ofNullable(null),Optional.of(r.getId()));
                    return new RestaurantResponse(r, ratingResponses, commentResponses);
                })
                .collect(Collectors.toList());
        return restaurantResponses;
    }



}