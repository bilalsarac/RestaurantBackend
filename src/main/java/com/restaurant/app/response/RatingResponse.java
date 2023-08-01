package com.restaurant.app.response;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.entities.Rating;
import lombok.Data;

@Data
public class RatingResponse {

    private double tasteScore;
    private double priceScore;
    private double serviceScore;

  /*  public RatingResponse(Rating entity){
        this.tasteScore = entity.getTasteScore();
        this.priceScore = entity.getPriceScore();
        this.serviceScore = entity.getServiceScore();
    }*/




}
