package com.restaurant.app.requests;

import lombok.Data;

@Data
public class UserRequest {

    private String email;
    private String password;
}