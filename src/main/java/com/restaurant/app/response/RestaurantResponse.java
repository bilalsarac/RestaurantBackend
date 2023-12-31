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
    private List<RatingResponse> ratings;
    private List<CommentResponse> comments;


    public RestaurantResponse(Restaurant entity, List<RatingResponse> ratings, List<CommentResponse> comments){
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.name = entity.getName();
        this.category = entity.getCategory();
        this.address = entity.getAddress();
        this.photoUrl = entity.getPhotoUrl();
        this.createDate = entity.getCreateDate();
        this.ratings = ratings;
        this.comments = comments;


    }

}
