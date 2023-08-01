package com.restaurant.app.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.restaurant.app.entities.User;
import com.restaurant.app.exceptions.UserNotFoundException;
import com.restaurant.app.response.UserResponse;
import com.restaurant.app.services.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //ok
    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers().stream().map(u -> new UserResponse(u)).toList();
    }
    //ok
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User newUser) {
        User user = userService.saveOneUser(newUser);
        if(user != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //ok
    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable Long userId) {
        User user = userService.getOneUserById(userId);
        if(user == null) {
            throw new UserNotFoundException();
        }
        return new UserResponse(user);
    }
    //non required
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<User> updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
        User user = userService.updateOneUser(userId, newUser);
        if(user != null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
    //non required
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('admin')")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound() {

    }
}