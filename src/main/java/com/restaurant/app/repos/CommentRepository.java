package com.restaurant.app.repos;

import com.restaurant.app.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByUserId(Long userId);


    List<Comment> findByRestaurantId(Long aLong);

    Comment findByUserIdAndRestaurantId(Long aLong, Long aLong1);


    void deleteByUserIdAndRestaurantId(Long userId, Long restaurantId);
}
