package com.restaurant.app.response;

import com.restaurant.app.entities.Rating;
import com.restaurant.app.entities.Restaurant;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RestaurantResponse {
    private Long id;
    private Long userId;
    private String name;
    private String category;
    private String address;
    private String photoUrl;
    private Date createDate;
    private List<Rating> ratings;


    public RestaurantResponse(Restaurant entity, List<Rating> ratings){
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.name = entity.getName();
        this.category = entity.getCategory();
        this.address = entity.getAddress();
        this.ratings = ratings;
        this.photoUrl = entity.getPhotoUrl();
        this.createDate = entity.getCreateDate();
    }

}
