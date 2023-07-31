package com.restaurant.app.repos;


import com.restaurant.app.entities.Rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {


    List<Rating> findByUserId(Long userId);


    List<Rating> findByRestaurantId(Long restaurantId);



}
