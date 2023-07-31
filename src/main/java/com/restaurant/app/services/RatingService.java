package com.restaurant.app.services;

import com.restaurant.app.entities.Rating;
import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.repos.RatingRepository;
import com.restaurant.app.requests.RatingCreateRequest;
import com.restaurant.app.requests.RatingUpdateRequest;
import com.restaurant.app.response.RatingResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private RatingRepository ratingRepository;
    private UserService userService;
    private RestaurantService restaurantService;


    public RatingService(RatingRepository ratingRepository, UserService userService,@Lazy RestaurantService restaurantService) {
        this.ratingRepository = ratingRepository;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }


    public List<RatingResponse> getAllRatings(Optional<Long> userId, Optional<Long> ratingId) {
        List<Rating> ratings;
         if(userId.isPresent()) {
            ratings = ratingRepository.findByUserId(userId.get());
        }else
            ratings = ratingRepository.findAll();
        return ratings.stream().map(rating -> new RatingResponse()).collect(Collectors.toList());
    }

    public Rating getOneRatingById(Long ratingId) {
       return ratingRepository.findById(ratingId).orElse(null);

    }

    public Rating createOneRating(RatingCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Restaurant restaurant = restaurantService.getOneRestaurantById(request.getRestaurantId());
        if(user != null && restaurant != null) {

            Rating ratingToSave = new Rating();
            ratingToSave.setId(request.getId());
           // ratingToSave.setRe(restaurant.getId());
            ratingToSave.setUser(user);
            ratingToSave.setServiceScore(request.getServiceScore());
            ratingToSave.setTasteScore(request.getTasteScore());
            ratingToSave.setPriceScore(request.getPriceScore());
            return ratingRepository.save(ratingToSave);
        }else
            return null;
    }



    public Rating updateOneRating(Long ratingId, RatingUpdateRequest request) {
        Optional<Rating> rating = ratingRepository.findById(ratingId);
        if(rating.isPresent()) {
            Rating commentToUpdate = rating.get();
            commentToUpdate.setPriceScore(request.getPriceScore());
            commentToUpdate.setTasteScore(request.getTasteScore());
            commentToUpdate.setServiceScore(request.getServiceScore());
            return ratingRepository.save(commentToUpdate);
        }else
            return null;
    }

    public void deleteOneRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }


    public void createRatingByUserIdAndRestaurantId(Long userId, Long restaurantId, RatingCreateRequest newRating) {
        User user = userService.getOneUserById(userId);
        Restaurant restaurant = restaurantService.getOneRestaurantById(restaurantId);

        Rating toSave = new Rating();
        toSave.setRestaurant(restaurant);
        toSave.setUser(user);
        toSave.setServiceScore(newRating.getServiceScore());
        toSave.setTasteScore(newRating.getTasteScore());
        toSave.setPriceScore(newRating.getPriceScore());
        ratingRepository.save(toSave);
    }

    public RatingResponse getRestaurantsAveragePoints(Long restaurantId) {
        double priceAverage = ratingRepository.getAveragePriceScoreForRestaurant(restaurantId);
        double tasteAverage = ratingRepository.getAverageTasteScoreForRestaurant(restaurantId);
        double serviceAverage = ratingRepository.getAverageServiceScoreForRestaurant(restaurantId);

        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setTasteScore(tasteAverage);
        ratingResponse.setPriceScore(priceAverage);
        ratingResponse.setServiceScore(serviceAverage);
        ratingResponse.setRestaurantId(restaurantId);

        return ratingResponse;
    }
}
