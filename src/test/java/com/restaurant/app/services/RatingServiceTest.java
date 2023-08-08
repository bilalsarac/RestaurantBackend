package com.restaurant.app.services;

import com.restaurant.app.entities.Rating;
import com.restaurant.app.entities.User;
import com.restaurant.app.repos.RatingRepository;
import com.restaurant.app.requests.RatingCreateRequest;
import com.restaurant.app.response.RatingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserService userService;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   /* @Test
    public void testGetAllRatings() {
        User user = new User();
        user.setId(856l);
        user.setEmail("asddas@gmaill.com");
        user.setPassword("123");
        Long userId = 1L;
        Long restaurantId = 2L;

        List<Rating> mockRatings = new ArrayList<>();
        Rating mockRating = new Rating();
        mockRating.setUser(user);

        mockRatings.add(mockRating);

        when(ratingRepository.findByUserIdAndRestaurantId(userId, restaurantId)).thenReturn(mockRating);

        List<RatingResponse> result = ratingService.getAllRatings(Optional.of(userId), Optional.of(restaurantId));

        assertEquals(1, result.size());
    }*/

  /*  @Test
    public void testGetOneRatingById() {
        Long ratingId = 1L;
        Rating rating = new Rating();
        rating.set(ratingId);

        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));

        Rating result = ratingService.getOneRatingById(ratingId);

        assertNotNull(result);
        assertEquals(ratingId, result.getId());
    }*/

    @Test
    public void testCreateOneRating() {
        RatingCreateRequest request = new RatingCreateRequest();
        request.setTasteScore(5d);
        request.setServiceScore(4d);
        request.setPriceScore(3d);

        Rating savedRating = new Rating();
        savedRating.setId(1L);
        savedRating.setTasteScore(request.getTasteScore());
        savedRating.setServiceScore(request.getServiceScore());
        savedRating.setPriceScore(request.getPriceScore());

        when(ratingRepository.save(any(Rating.class))).thenReturn(savedRating);

        Rating result = ratingService.createOneRating(request);

        assertNotNull(result);
        assertEquals(savedRating.getId(), result.getId());
        assertEquals(request.getTasteScore(), result.getTasteScore());
        assertEquals(request.getServiceScore(), result.getServiceScore());
        assertEquals(request.getPriceScore(), result.getPriceScore());
    }


}
