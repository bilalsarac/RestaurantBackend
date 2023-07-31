package com.restaurant.app.repos;

import com.restaurant.app.entities.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

    List<Restaurant> findByUserId(Long userId);

    List<Restaurant> findAll(Sort sort);


}
