package com.restaurant.app.requests;

import lombok.Data;

@Data
public class RatingCreateRequest {
    private Long id;
    private Double priceScore;
    private Double tasteScore;
    private Double serviceScore;
}
