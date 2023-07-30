package com.restaurant.app.response;

import com.restaurant.app.entities.Comment;
import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private Long userId;
    private String text;
    private String email;


    public CommentResponse(Comment entity){
        this.id= entity.getId();
        this.userId = entity.getUser().getId();
        this.text = entity.getText();
        this.email = entity.getUser().getEmail();
    }
}