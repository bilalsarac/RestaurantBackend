package com.restaurant.app.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


import java.util.Optional;

import com.restaurant.app.entities.Restaurant;
import com.restaurant.app.entities.User;
import com.restaurant.app.repos.RestaurantRepository;
import com.restaurant.app.requests.RestaurantCreateRequest;
import com.restaurant.app.requests.RestaurantUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserService userService;

    @Mock
    private RatingServiceTest ratingService;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void getOneRestaurantById() {
        Restaurant restaurant = createRestaurant(1L, "Test Restaurant");

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        Restaurant result = restaurantService.getOneRestaurantById(1L);

        assertNotNull(result);
        assertEquals("Test Restaurant", result.getName());
    }

    @Test
    public void saveOneRestaurant() {

        User user = new User();
        user.setEmail("bilal11@gmail.com");
        user.setPassword("123");
        user.setRole("user");
        userService.saveOneUser(user);


        RestaurantCreateRequest newRestaurant = new RestaurantCreateRequest();
        newRestaurant.setName("New Restaurant");
        newRestaurant.setCategory("Category");
        newRestaurant.setAddress("Address");
        newRestaurant.setPhotoUrl("Photo");
        newRestaurant.setUserId(1L);

        when(userService.getOneUserById(1L)).thenReturn(user);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(createRestaurant(1L, "New Restaurant"));

        Restaurant result = restaurantService.saveOneRestaurant(newRestaurant);

        assertNotNull(result);
        assertEquals("New Restaurant", result.getName());
    }

    @Test
    public void updateOneRestaurant() {
        Restaurant existingRestaurant = createRestaurant(1L, "Old Restaurant");
        RestaurantUpdateRequest newRestaurant = new RestaurantUpdateRequest();
        newRestaurant.setName("Updated Restaurant");

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(existingRestaurant));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(createRestaurant(1L, "Updated Restaurant"));

        Restaurant result = restaurantService.updateOneRestaurant(1L, newRestaurant);

        assertNotNull(result);
        assertEquals("Updated Restaurant", result.getName());
    }

    private Restaurant createRestaurant(Long id, String name) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName(name);

        return restaurant;
    }
}


