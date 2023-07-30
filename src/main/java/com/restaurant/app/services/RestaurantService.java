package com.restaurant.app.services;

import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.repos.RestaurantRepository;
import com.restaurant.app.requests.RestaurantCreateRequest;
import com.restaurant.app.requests.RestaurantUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private UserService userService;


    public RestaurantService(RestaurantRepository restaurantRepository, UserService userService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants;
        restaurants = restaurantRepository.findAll();
        return restaurants;
    }

    public Restaurant getOneRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElse(null);
    }

    public Restaurant saveOneRestaurant(RestaurantCreateRequest newRestaurant) {
        User user = userService.getOneUserById(newRestaurant.getUserId());
        if(user == null)
            return null;
        Restaurant toSave = new Restaurant();
        toSave.setId(newRestaurant.getId());
        toSave.setAddress(newRestaurant.getAddress());
        toSave.setName(newRestaurant.getName());
        toSave.setCategory(newRestaurant.getCategory());
        toSave.setPhotoUrl(newRestaurant.getPhotoUrl());
        toSave.setUser(user);
        toSave.setCreateDate(new Date());
        return restaurantRepository.save(toSave);
    }



    public Restaurant updateOneRestaurant(Long restaurantId, RestaurantUpdateRequest newRestaurant) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if(restaurant.isPresent()) {
            Restaurant toUpdate = restaurant.get();
            toUpdate.setName(newRestaurant.getName());
            toUpdate.setCategory(newRestaurant.getCategory());
            toUpdate.setAddress(newRestaurant.getAddress());
            toUpdate.setPhotoUrl(newRestaurant.getPhotoUrl());

            restaurantRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteOneRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
