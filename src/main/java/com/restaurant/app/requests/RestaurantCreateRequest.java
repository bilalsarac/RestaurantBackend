package com.restaurant.app.requests;


import lombok.Data;

import java.util.Date;

@Data
public class RestaurantCreateRequest {

    private Long id;
    private String name;
    private String category;
    private String address;
    private Long userId;
    private String photoUrl;

}