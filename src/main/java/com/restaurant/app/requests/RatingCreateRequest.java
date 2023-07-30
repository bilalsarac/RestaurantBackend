package com.restaurant.app.requests;

import lombok.Data;

@Data
public class RatingCreateRequest {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private int priceScore;
    private int tasteScore;
    private int serviceScore;
}
