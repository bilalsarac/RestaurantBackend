package com.restaurant.app.response;

import com.restaurant.app.entities.User;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String email;
    private String role;
    private String photoUrl;

    public UserResponse(User entity) {
        this.id = entity.getId();
        this.photoUrl = entity.getPhotoUrl();
        this.email = entity.getEmail();
        this.role = entity.getRole();
    }
}