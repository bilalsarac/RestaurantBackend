package com.restaurant.app.controllers;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.requests.CommentCreateRequest;
import com.restaurant.app.requests.CommentUpdateRequest;
import com.restaurant.app.response.CommentResponse;
import com.restaurant.app.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //ok
    @GetMapping
    public List<CommentResponse> getAllComments(@RequestParam Optional<Long> userId,
                                                @RequestParam Optional<Long> restaurantId) {
        return commentService.getAllCommentsWithParam(userId, restaurantId);
    }
    @GetMapping("/comment")
    public ResponseEntity<CommentResponse> getComment(@RequestParam Long userId,
                                                      @RequestParam Long restaurantId) {
        CommentResponse commentResponse = commentService.getComment(userId, restaurantId);
        if (commentResponse != null) {
            return ResponseEntity.ok(commentResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //ok
    @PostMapping
    public String createOneComment(@RequestBody CommentCreateRequest request) {
        Comment comment =  commentService.createOneComment(request);
        if(comment == null){
            return "Bad request";
        }
        else{
            return "Created successfully.";
        }
    }

    //ok
    @GetMapping("/{commentId}")
    public CommentResponse getOneComment(@PathVariable Long commentId) {
        return commentService.getOneCommentById(commentId);
    }

    //ok
    // changed pathvariable to requestparam
    @PutMapping()
    public CommentResponse editOneCommentByRestaurantAndUserIds(@RequestParam Long userId,@RequestParam Long restaurantId,@RequestBody CommentUpdateRequest updatedComment) {
         return commentService.editOneCommentByRestaurantAndUserIds(userId,restaurantId,updatedComment);
    }

    //ok
    // changed pathvariable to requestparam
    @DeleteMapping()
    public ResponseEntity<String> deleteOneCommentByRestaurantAndUserIds(@RequestParam Long userId, @RequestParam Long restaurantId) {

         commentService.deleteOneCommentByRestaurantAndUserIds(userId, restaurantId);
         return ResponseEntity.ok("Comment deleted successfully");
    }
}