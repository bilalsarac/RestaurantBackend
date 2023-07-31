package com.restaurant.app.repos;

import com.restaurant.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {


    User findByEmail(String email);



}