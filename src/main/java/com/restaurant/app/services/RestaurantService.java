package com.restaurant.app.services;


import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.repos.RestaurantRepository;
import com.restaurant.app.requests.RestaurantCreateRequest;
import com.restaurant.app.requests.RestaurantUpdateRequest;
import com.restaurant.app.response.RatingResponse;
import com.restaurant.app.response.RestaurantResponse;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private UserService userService;
    private RatingService ratingService;


    public RestaurantService(RestaurantRepository restaurantRepository, UserService userService, RatingService ratingService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.ratingService = ratingService;
    }

    public List<RestaurantResponse> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));

        return restaurants.stream().map(restaurant -> {
            List<RatingResponse> ratings = ratingService.getAllRatings(Optional.ofNullable(null), Optional.of(restaurant.getId()));
            return new RestaurantResponse(restaurant, ratings);
        }).collect(Collectors.toList());
    }

    public Restaurant getOneRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
       return restaurant;

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
        List<RatingResponse> ratings = ratingService.getAllRatings(Optional.empty(), Optional.of(restaurantId));
        ratingService.deleteRatingsByRestaurantId(restaurantId);


        restaurantRepository.deleteById(restaurantId);
    }

    public List<RestaurantResponse> getRestaurantsByUserId(Long userId) {
        List<Restaurant> restaurants = restaurantRepository.findByUserId(userId);
        return restaurants.stream().map(r -> {
            List<RatingResponse> ratings = ratingService.getAllRatings(Optional.ofNullable(null), Optional.of(r.getId()));
            return new RestaurantResponse(r, ratings);}).collect(Collectors.toList());

    }


}
