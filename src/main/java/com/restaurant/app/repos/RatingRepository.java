package com.restaurant.app.repos;


import com.restaurant.app.entities.Rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {


    List<Rating> findByUserId(Long userId);


    List<Rating> findByRestaurantId(Long restaurantId);

    @Query(value = "SELECT AVG(price_score) FROM score WHERE restaurant_id = ?1", nativeQuery = true)
    Double getAveragePriceScoreForRestaurant(Long restaurantId);

    @Query(value = "SELECT AVG(service_score) FROM score WHERE restaurant_id = ?1", nativeQuery = true)
    Double getAverageServiceScoreForRestaurant(Long restaurantId);

    @Query(value = "SELECT AVG(taste_score) FROM score WHERE restaurant_id = ?1", nativeQuery = true)
    Double getAverageTasteScoreForRestaurant(Long restaurantId);

    Rating findByUserIdAndRestaurantId(Long userId, Long restaurantId);


    void deleteByUserIdAndRestaurantId(Long userId, Long restaurantId);

    void deleteRatingsByRestaurantId(Long restaurantId);
}
