package com.restaurant.app.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.repos.CommentRepository;
import com.restaurant.app.requests.RestaurantCreateRequest;
import com.restaurant.app.response.CommentResponse;
import com.restaurant.app.response.RestaurantResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CommentService commentService;
    @Mock
    private RestaurantService restaurantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllCommentsWithParam() {

        User user = new User();
        user.setEmail("bilal9@gmail.com");
        user.setPassword("123");
        user.setRole("user");
        userService.saveOneUser(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setPhotoUrl("photoo");
        restaurant.setAddress("address");
        restaurant.setName("restaurant");
        restaurant.setCategory("category");
        restaurant.setId(user.getId());

        Comment comment = new Comment();
        comment.setText("comment");
        comment.setRestaurant(restaurant);
        comment.setUser(user);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);


        when(commentRepository.findByUserId(anyLong())).thenReturn(comments);
        when(commentRepository.findByRestaurantId(anyLong())).thenReturn(comments);
        when(commentRepository.findAll()).thenReturn(comments);

        List<CommentResponse> result1 = commentService.getAllCommentsWithParam(Optional.of(1L), Optional.empty());
        assertEquals(1, result1.size());

        List<CommentResponse> result2 = commentService.getAllCommentsWithParam(Optional.empty(), Optional.of(1L));
        assertEquals(1, result2.size());

        List<CommentResponse> result3 = commentService.getAllCommentsWithParam(Optional.empty(), Optional.empty());
        assertEquals(1, result3.size());
    }

   /* @Test
    public void getOneCommentById() {
        User user = new User();
        user.setId(80l);
        user.setEmail("asdasd@gmail.com");
        user.setRole("admin");
        user.setPassword("123");
        userService.saveOneUser(user);



        RestaurantCreateRequest restaurantCreateRequest = new RestaurantCreateRequest();
        restaurantCreateRequest.setId(9l);
        restaurantCreateRequest.setUserId(user.getId());
        restaurantCreateRequest.setName("asd");

        Restaurant restaurant = restaurantService.saveOneRestaurant(restaurantCreateRequest);

        Restaurant restaurant1 = restaurantService.getOneRestaurantById(restaurant.getId());


        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Test comment");
        comment.setUser(user);
        comment.setRestaurant(restaurant1);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentResponse result = commentService.getOneCommentById(1L);

        assertNotNull(result);
        assertEquals("Test comment", result.getText());
    }
*/
}
