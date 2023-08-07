package com.restaurant.app.controllers;

import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.requests.RestaurantCreateRequest;
import com.restaurant.app.requests.RestaurantUpdateRequest;
import com.restaurant.app.response.RestaurantResponse;
import com.restaurant.app.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
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
}