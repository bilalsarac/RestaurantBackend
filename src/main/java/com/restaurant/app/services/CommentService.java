package com.restaurant.app.services;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.repos.CommentRepository;
import com.restaurant.app.requests.CommentCreateRequest;
import com.restaurant.app.requests.CommentUpdateRequest;
import com.restaurant.app.response.CommentResponse;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private RestaurantService restaurantService;
    public CommentService(CommentRepository commentRepository, UserService userService,
                          RestaurantService restaurantService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    public List<CommentResponse> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> restaurantId) {
        List<Comment> comments;
        if (userId.isPresent() && restaurantId.isPresent()) {
            Comment comment = commentRepository.findByUserIdAndRestaurantId(userId.get(), restaurantId.get());
            if (comment == null) {
                comments = new ArrayList<>();
            } else {
                comments = Collections.singletonList(comment);
            }
        } else if (userId.isPresent()) {
            comments = commentRepository.findByUserId(userId.get());
        } else if (restaurantId.isPresent()) {
            comments = commentRepository.findByRestaurantId(restaurantId.get());
        } else {
            comments = commentRepository.findAll();
        }
        return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
    }



    public CommentResponse getOneCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        CommentResponse commentResponse = new CommentResponse(comment);
        return commentResponse;
    }

    public CommentResponse getComment(Long userId,Long restaurantId) {
        Comment comment = commentRepository.findByUserIdAndRestaurantId(userId,restaurantId);
        if(comment!= null){
            CommentResponse commentResponse = new CommentResponse(comment);
            return commentResponse;
        }else{
            return null;
        }

    }

    public Comment createOneComment(CommentCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Restaurant restaurant = restaurantService.getOneRestaurantById(request.getRestaurantId());
        Comment existingComment = commentRepository.findByUserIdAndRestaurantId(request.getUserId(), request.getRestaurantId());
        if (existingComment != null) {
            return null;
        }
        if(user != null && restaurant != null) {

            Comment commentToSave = new Comment();
            commentToSave.setId(request.getId());
            commentToSave.setRestaurant(restaurant);
            commentToSave.setUser(user);
            commentToSave.setText(request.getText());
            commentToSave.setCreateDate(new Date());
            return commentRepository.save(commentToSave);
        }else
            return null;
    }

    @Transactional
    public void deleteOneCommentByRestaurantAndUserIds(Long userId, Long restaurantId) {
        commentRepository.deleteByUserIdAndRestaurantId(userId, restaurantId);

    }

    public CommentResponse editOneCommentByRestaurantAndUserIds(Long userId, Long restaurantId, CommentUpdateRequest newComment) {
        Comment comment = commentRepository.findByUserIdAndRestaurantId(userId, restaurantId);
        Comment commentToUpdate = comment;
        commentToUpdate.setText(newComment.getText());
        commentRepository.save(commentToUpdate);
        CommentResponse commentResponse = new CommentResponse(commentToUpdate);
        return commentResponse;
    }


}