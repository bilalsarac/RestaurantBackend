package com.restaurant.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.restaurant.app.entities.User;
import com.restaurant.app.repos.CommentRepository;
import com.restaurant.app.repos.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    UserRepository userRepository;
    CommentRepository commentRepository;



    public UserService(UserRepository userRepository,
                       CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User foundUser = user.get();

            foundUser.setRole(newUser.getRole());
            userRepository.save(foundUser);
            return foundUser;
        }else
            return null;
    }

    public void deleteById(Long userId) {
        try {

            userRepository.deleteById(userId);
        }catch(EmptyResultDataAccessException e) {
            System.out.println("User "+userId+" doesn't exist");
        }
    }
/*
    public User getOneUserByUserName(String email) {
        return userRepository.findByEmail(email);
    }
*/
    public User getOneUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    /**  public List<User> searchUsers(String name) {

        return userRepository.findByKeyword(name);


    }*/
}