package com.restaurant.app.response;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.entities.Rating;
import lombok.Data;

@Data
public class RatingResponse {

    private Double tasteScore;
    private Double priceScore;
    private Double serviceScore;

}
