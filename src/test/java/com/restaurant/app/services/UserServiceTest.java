package com.restaurant.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.restaurant.app.entities.User;
import com.restaurant.app.repos.CommentRepository;
import com.restaurant.app.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    public void saveOneUser() {
        User newUser = new User();
        newUser.setEmail("example@gmail.com");
        newUser.setRole("user");

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User result = userService.saveOneUser(newUser);

        assertEquals("example@gmail.com", result.getEmail());
        assertEquals("user", result.getRole());
    }

    @Test
    public void getOneUserById() {
        User user = new User();
        user.setId(1L);
        user.setEmail("example@gmail.com");
        user.setRole("user");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getOneUserById(1L);

        assertEquals("example@gmail.com", result.getEmail());
        assertEquals("user", result.getRole());
    }

    @Test
    public void updateOneUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setRole("user");

        User updatedUser = new User();
        updatedUser.setRole("admin");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateOneUser(1L, updatedUser);

        assertEquals("admin", result.getRole());
    }

    @Test
    public void deleteUserById() {
        // Assuming user with ID 1 exists
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(eq(userId));

        userService.deleteById(userId);

        verify(userRepository, times(1)).deleteById(eq(userId));
    }

    @Test
    public void getOneUserByEmail() {
        User user = new User();
        user.setEmail("example@gmail.com");
        user.setRole("user");

        when(userRepository.findByEmail("example@gmail.com")).thenReturn(user);

        User result = userService.getOneUserByEmail("example@gmail.com");

        assertEquals("example@gmail.com", result.getEmail());
        assertEquals("user", result.getRole());
    }


}
