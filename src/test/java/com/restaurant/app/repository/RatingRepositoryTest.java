package com.restaurant.app.repository;

import com.restaurant.app.entities.Rating;
import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.repos.RatingRepository;
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
public class RatingRepositoryTest {



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RatingRepository ratingRepository;

    @Test
    public void ratingSaveCheck() {

        User user = new User();
        user.setEmail("bilal@gmail.com");
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



        Rating rating = new Rating();
        rating.setTasteScore(5);
        rating.setServiceScore(5);
        rating.setPriceScore(5);
        rating.setRestaurant(restaurant);
        rating.setUser(user);


        Rating saveComment = ratingRepository.save(rating);

        assertThat(saveComment).isNotNull();
        assertThat(saveComment.getUser()).isEqualTo(rating.getUser());
        assertThat(saveComment.getTasteScore()).isEqualTo(rating.getTasteScore());
        assertThat(saveComment.getRestaurant()).isEqualTo(rating.getRestaurant());
    }

    // DB should be empty
    @Test
    public void getAllRatings() {

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

        Rating rating1 = new Rating();
        rating1.setTasteScore(5);
        rating1.setServiceScore(5);
        rating1.setPriceScore(5);
        rating1.setRestaurant(restaurant);
        rating1.setUser(user);

        ratingRepository.save(rating1);

        Rating rating2 = new Rating();
        rating2.setPriceScore(8);
        rating2.setRestaurant(restaurant);
        rating2.setUser(user);

        ratingRepository.save(rating2);

        List<Rating> ratings = ratingRepository.findAll();
        assertThat(ratings).hasSize(2);
        assertThat(ratings).contains(rating1,rating2);
    }
    @Test
    public void deleteRating() {
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
        restaurantRepository.save(restaurant);

        Rating rating = new Rating();
        rating.setTasteScore(5);
        rating.setServiceScore(5);
        rating.setPriceScore(5);
        rating.setRestaurant(restaurant);
        rating.setUser(user);
        Rating savedRating = ratingRepository.save(rating);
        Long userId = savedRating.getId();

        ratingRepository.deleteById(userId);

        Optional<Rating> deletedUser = ratingRepository.findById(userId);
        assertThat(deletedUser).isEmpty();
    }
}
