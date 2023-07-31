package com.restaurant.app.controllers;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.requests.CommentCreateRequest;
import com.restaurant.app.requests.CommentUpdateRequest;
import com.restaurant.app.response.CommentResponse;
import com.restaurant.app.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> getAllComments(@RequestParam Optional<Long> userId,
                                                @RequestParam Optional<Long> restaurantId) {
        return commentService.getAllCommentsWithParam(userId, restaurantId);
    }

    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest request) {
        return commentService.createOneComment(request);
    }

    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId) {
        return commentService.getOneCommentById(commentId);
    }

    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
        return commentService.updateOneCommentById(commentId, request);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId) {
        commentService.deleteOneCommentById(commentId);
    }

    @PutMapping("/{userId}/{restaurantId}")
    public void editOneCommentByRestaurantAndUserIds(@PathVariable Long userId,@PathVariable Long restaurantId,@RequestBody CommentCreateRequest newComment) {
        commentService.editOneCommentByRestaurantAndUserIds(userId,restaurantId,newComment);
    }

    @DeleteMapping("/{userId}/{restaurantId}")
    public void deleteOneCommentByRestaurantAndUserIds(@PathVariable Long userId,@PathVariable Long restaurantId) {
        commentService.deleteOneCommentByRestaurantAndUserIds(userId,restaurantId);
    }

}