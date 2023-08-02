package com.restaurant.app.repository;

import com.restaurant.app.entities.Comment;
import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.repos.CommentRepository;
import com.restaurant.app.repos.RestaurantRepository;
import com.restaurant.app.repos.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTest {


    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void commentSaveCheck() {

        User user = new User();
        user.setEmail("bilal5@gmail.com");
        user.setPassword("123");
        user.setRole("user");


        Restaurant restaurant = new Restaurant();
        restaurant.setPhotoUrl("photo");
        restaurant.setAddress("address");
        restaurant.setName("restaurant");
        restaurant.setCategory("category");
        restaurant.setUser(user);

        userRepository.save(user);
        restaurantRepository.save(restaurant);



        Comment comment = new Comment();
        comment.setText("comment");
        comment.setRestaurant(restaurant);
        comment.setUser(user);


        Comment saveComment = commentRepository.save(comment);

        assertThat(saveComment).isNotNull();
        assertThat(saveComment.getUser()).isEqualTo(comment.getUser());
        assertThat(saveComment.getText()).isEqualTo(comment.getText());
        assertThat(saveComment.getRestaurant()).isEqualTo(comment.getRestaurant());
    }

    // DB should be empty
   /* @Test
    public void getAllComments() {

        User user = new User();
        user.setEmail("bilal@gmail.com");
        user.setPassword("123");
        user.setRole("user");

        userRepository.save(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setPhotoUrl("photo");
        restaurant.setAddress("address");
        restaurant.setName("restaurant");
        restaurant.setCategory("category");
        restaurant.setUser(user);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setPhotoUrl("photo");
        restaurant2.setAddress("address");
        restaurant2.setName("restaurant");
        restaurant2.setCategory("category");
        restaurant2.setUser(user);


        restaurantRepository.save(restaurant);
        restaurantRepository.save(restaurant2);

        Comment comment1 = new Comment();
        comment1.setText("comment");
        comment1.setRestaurant(restaurant);
        comment1.setUser(user);

        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setText("comment");
        comment2.setRestaurant(restaurant2);
        comment2.setUser(user);

         commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(2);
        assertThat(comments).contains(comment1, comment2);
    }*/
    @Test
    public void deleteComment() {
        User user = new User();
        user.setEmail("bilal7@gmail.com");
        user.setPassword("123");
        user.setRole("user");

        userRepository.save(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setPhotoUrl("photo");
        restaurant.setAddress("address");
        restaurant.setName("restaurant");
        restaurant.setCategory("category");
        restaurant.setUser(user);
        restaurantRepository.save(restaurant);

        Comment comment = new Comment();
        comment.setCreateDate(new Date());
        comment.setText("comment");
        comment.setRestaurant(restaurant);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        Long userId = savedComment.getId();

        commentRepository.deleteById(userId);

        Optional<Comment> deletedUser = commentRepository.findById(userId);
        assertThat(deletedUser).isEmpty();
    }
}
