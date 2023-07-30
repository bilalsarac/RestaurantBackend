package com.restaurant.app.response;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.entities.Rating;
import lombok.Data;

@Data
public class RatingResponse {
    private Long id;
    private Long userId;
    private int tasteScore;
    private int priceScore;
    private int serviceScore;
    private String email;



    public RatingResponse(Rating entity){
        this.id= entity.getId();
        this.userId = entity.getUser().getId();
        this.serviceScore = entity.getServiceScore();
        this.tasteScore = entity.getTasteScore();
        this.priceScore = entity.getPriceScore();
        this.email = entity.getUser().getEmail();
    }
}
