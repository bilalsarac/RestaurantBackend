package com.restaurant.app.requests;

import lombok.Data;

@Data
public class RatingUpdateRequest {

    private Double serviceScore;
    private Double tasteScore;
    private Double priceScore;
}
