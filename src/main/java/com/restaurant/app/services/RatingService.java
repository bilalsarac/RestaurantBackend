package com.restaurant.app.services;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.entities.Rating;
import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.repos.RatingRepository;
import com.restaurant.app.requests.RatingCreateRequest;
import com.restaurant.app.requests.RatingUpdateRequest;
import com.restaurant.app.response.CommentResponse;
import com.restaurant.app.response.RatingResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        if(userId.isPresent()) {
            ratings = ratingRepository.findByUserId(userId.get());
        }else if(restaurantId.isPresent()){
            ratings = ratingRepository.findByRestaurantId(restaurantId.get());
        }

        else
            ratings = ratingRepository.findAll();
        return ratings.stream().map(rating -> new RatingResponse()).collect(Collectors.toList());
    }

    public Rating getOneRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId).orElse(null);

    }

    public Rating createOneRating(RatingCreateRequest request) {

        Rating ratingToSave = new Rating();
        ratingToSave.setId(request.getId());
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
        Double priceAverage = ratingRepository.getAveragePriceScoreForRestaurant(restaurantId);
        Double tasteAverage = ratingRepository.getAverageTasteScoreForRestaurant(restaurantId);
        Double serviceAverage = ratingRepository.getAverageServiceScoreForRestaurant(restaurantId);

        RatingResponse ratingResponse = new RatingResponse();

        if (priceAverage != null) {
            ratingResponse.setPriceScore(priceAverage);
        } else {
            ratingResponse.setPriceScore(null);
        }

        if (tasteAverage != null) {
            ratingResponse.setTasteScore(tasteAverage);
        } else {
            ratingResponse.setTasteScore(null);
        }

        if (serviceAverage != null) {
            ratingResponse.setServiceScore(serviceAverage);
        } else {
            ratingResponse.setServiceScore(null);
        }
        return ratingResponse;
    }

    public RatingResponse getOneRatingByRestaurantAndUserIds(Long userId, Long restaurantId) {
        Rating rating =  ratingRepository.findByUserIdAndRestaurantId(userId, restaurantId);

        RatingResponse ratingResponse = new RatingResponse();

        ratingResponse.setTasteScore(rating.getTasteScore());
        ratingResponse.setServiceScore(rating.getServiceScore());
        ratingResponse.setPriceScore(rating.getPriceScore());


        return ratingResponse;
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