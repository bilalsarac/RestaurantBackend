package com.restaurant.app.repos;

import com.restaurant.app.entities.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

    List<Restaurant> findByUserId(Long userId);

    List<Restaurant> findAll(Sort sort);

    @Query(value = "select id,name,category,address,photo_url,create_date,user_id from restaurant r where " +
            "lower(r.id) like lower(concat('%', :keyword, '%')) " +
            "or lower(r.name) like lower(concat('%', :keyword, '%'))" +
            "or lower(r.address) like lower(concat('%', :keyword, '%'))" +
            "or lower(r.category) like lower(concat('%', :keyword, '%'))",nativeQuery = true)
    List<Restaurant> findByKeyword(@Param("keyword") String keyword);
}
