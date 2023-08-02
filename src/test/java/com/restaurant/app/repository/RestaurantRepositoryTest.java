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
public class RestaurantRepositoryTest {



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;


    @Test
    public void restaurantSaveCheck() {

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


        Restaurant restaurant1 = restaurantRepository.save(restaurant);


        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getUser()).isEqualTo(restaurant1.getUser());
        assertThat(restaurant.getCategory()).isEqualTo(restaurant1.getCategory());
        assertThat(restaurant.getName()).isEqualTo(restaurant1.getName());
        assertThat(restaurant.getAddress()).isEqualTo(restaurant1.getAddress());
        assertThat(restaurant.getPhotoUrl()).isEqualTo(restaurant1.getPhotoUrl());
    }

    // DB should be empty
    @Test
    public void getAllRestaurants() {

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



        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertThat(restaurants).hasSize(2);
        assertThat(restaurants).contains(restaurant,restaurant2);
    }
    @Test
    public void deleteRestaurant() {
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

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        Long userId = savedRestaurant.getId();

        restaurantRepository.deleteById(userId);

        Optional<Restaurant> deletedUser = restaurantRepository.findById(userId);
        assertThat(deletedUser).isEmpty();
    }
}
