package com.restaurant.app.requests;

import lombok.Data;

@Data
public class RatingCreateRequest {
    private Long id;
    private int priceScore;
    private int tasteScore;
    private int serviceScore;
}
