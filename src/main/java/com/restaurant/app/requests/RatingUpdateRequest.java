package com.restaurant.app.requests;

import lombok.Data;

@Data
public class RatingUpdateRequest {

    private int serviceScore;
    private int tasteScore;
    private int priceScore;
}
