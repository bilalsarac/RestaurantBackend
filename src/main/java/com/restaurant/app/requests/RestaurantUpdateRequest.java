package com.restaurant.app.requests;

import lombok.Data;

@Data
public class RestaurantUpdateRequest {

    private String name;
    private String category;
    private String address;
    private String photoUrl;
}
