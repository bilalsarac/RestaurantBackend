package com.restaurant.app.repos;


import com.restaurant.app.entities.Rating;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {


    List<Rating> findByUserId(Long userId);



}
