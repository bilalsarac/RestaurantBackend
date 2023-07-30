package com.restaurant.app.controllers;

import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.exceptions.UserNotFoundException;
import com.restaurant.app.requests.RestaurantCreateRequest;
import com.restaurant.app.requests.RestaurantUpdateRequest;
import com.restaurant.app.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants(){
        return restaurantService.getAllRestaurants();
    }

    @PostMapping
    public ResponseEntity<Void> createRestaurant(@RequestBody RestaurantCreateRequest newRestaurant) {
        Restaurant restaurant = restaurantService.saveOneRestaurant(newRestaurant);
        if(restaurant != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{restaurantId}")
    public Restaurant getOneRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.getOneRestaurantById(restaurantId);
        if(restaurant == null) {
            throw new UserNotFoundException();
        }
        return restaurant;
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<User> updateOneRestaurant(@PathVariable Long restaurantId, @RequestBody RestaurantUpdateRequest newRestaurant) {
        Restaurant restaurant = restaurantService.updateOneRestaurant(restaurantId, newRestaurant);
        if(restaurant != null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/{restaurantId}")
    public void deleteOneRestaurant(@PathVariable Long restaurantId) {
        restaurantService.deleteOneRestaurant(restaurantId);
    }
}
