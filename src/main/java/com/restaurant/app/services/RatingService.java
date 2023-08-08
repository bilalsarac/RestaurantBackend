package com.restaurant.app.services;

import com.restaurant.app.entities.Rating;
import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.repos.RatingRepository;
import com.restaurant.app.requests.RatingCreateRequest;
import com.restaurant.app.requests.RatingUpdateRequest;
import com.restaurant.app.response.AverageResponse;
import com.restaurant.app.response.RatingResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
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


    public List<RatingResponse> getAllRatings(Optional<Long> userId, Optional<Long> restaurantId) {
        List<Rating> ratings;
        if(userId.isPresent() && restaurantId.isPresent()){
            Rating rating = ratingRepository.findByUserIdAndRestaurantId(userId.get(),restaurantId.get());
            if (rating == null) {
                ratings = new ArrayList<>();
            } else {
                ratings = Collections.singletonList(rating);
            }
        }
        else if(userId.isPresent()) {
            ratings = ratingRepository.findByUserId(userId.get());
        }else if(restaurantId.isPresent()){
            ratings = ratingRepository.findByRestaurantId(restaurantId.get());
        }
        else{
            ratings = ratingRepository.findAll();
        }

        return ratings.stream().map(rating -> new RatingResponse(rating)).collect(Collectors.toList());
    }

    public RatingResponse getOneRatingById(Long ratingId) {
        Rating rating =  ratingRepository.findById(ratingId).orElse(null);

        RatingResponse ratingResponse = new RatingResponse(rating);

        return ratingResponse;

    }

    public Rating createOneRating(RatingCreateRequest request) {

        User user = userService.getOneUserById(request.getUserId());
        Restaurant restaurant = restaurantService.getOneRestaurantById(request.getRestaurantId());

        Rating ratingToSave = new Rating();
        ratingToSave.setId(request.getId());
        ratingToSave.setUser(user);
        ratingToSave.setRestaurant(restaurant);
        ratingToSave.setServiceScore(request.getServiceScore());
        ratingToSave.setTasteScore(request.getTasteScore());
        ratingToSave.setPriceScore(request.getPriceScore());
        return ratingRepository.save(ratingToSave);

    }


    public Rating updateOneRating(Long userId,Long restaurantId, RatingUpdateRequest request) {

        Rating rating = ratingRepository.findByUserIdAndRestaurantId(userId,restaurantId);

        Rating commentToUpdate = rating;
        commentToUpdate.setPriceScore(request.getPriceScore());
        commentToUpdate.setTasteScore(request.getTasteScore());
        commentToUpdate.setServiceScore(request.getServiceScore());
        return ratingRepository.save(commentToUpdate);

    }

    public AverageResponse getRestaurantsAveragePoints(Long restaurantId) {

        Double priceAverage = ratingRepository.getAveragePriceScoreForRestaurant(restaurantId);
        Double tasteAverage = ratingRepository.getAverageTasteScoreForRestaurant(restaurantId);
        Double serviceAverage = ratingRepository.getAverageServiceScoreForRestaurant(restaurantId);

        AverageResponse averageResponse = new AverageResponse();

        if (priceAverage != null) {
            averageResponse.setPriceScore(priceAverage);
        } else {
            averageResponse.setPriceScore(null);
        }

        if (tasteAverage != null) {
            averageResponse.setTasteScore(tasteAverage);
        } else {
            averageResponse.setTasteScore(null);
        }

        if (serviceAverage != null) {
            averageResponse.setServiceScore(serviceAverage);
        } else {
            averageResponse.setServiceScore(null);
        }
        return averageResponse;
    }

    public RatingResponse getOneRatingByRestaurantAndUserIds(Long userId, Long restaurantId) {
        Rating rating =  ratingRepository.findByUserIdAndRestaurantId(userId, restaurantId);
        if(rating!=null){
            RatingResponse ratingResponse = new RatingResponse(rating);
            return ratingResponse;
        }else{
            return null;
        }

    }
    @Transactional
    public void deleteOneRating(Long userId, Long restaurantId) {
        ratingRepository.deleteByUserIdAndRestaurantId(userId,restaurantId);
    }
    @Transactional
    public void deleteRatingsByRestaurantId(Long restaurantId){
        ratingRepository.deleteRatingsByRestaurantId(restaurantId);
    }
}