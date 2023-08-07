package com.restaurant.app.response;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.entities.Rating;
import lombok.Data;

@Data
public class RatingResponse {

    private Long userId;
    private Long restaurantId;
    private Double tasteScore;
    private Double priceScore;
    private Double serviceScore;

    public RatingResponse(Rating entity){
        this.userId = entity.getUser().getId();
        this.restaurantId = entity.getRestaurant().getId();
        this.tasteScore = entity.getTasteScore();
        this.priceScore = entity.getPriceScore();
        this.serviceScore = entity.getServiceScore();
    }

}
