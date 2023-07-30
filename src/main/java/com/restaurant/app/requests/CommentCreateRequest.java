package com.restaurant.app.requests;

import lombok.Data;

@Data
public class CommentCreateRequest {

    private Long id;
    private Long userId;
    private Long restaurantId;
    private String text;
}
