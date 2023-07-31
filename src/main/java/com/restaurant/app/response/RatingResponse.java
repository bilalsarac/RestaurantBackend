package com.restaurant.app.response;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.entities.Rating;
import lombok.Data;

@Data
public class RatingResponse {
    private Long id;
    private double tasteScore;
    private double priceScore;
    private double serviceScore;
    private Long restaurantId;




}
