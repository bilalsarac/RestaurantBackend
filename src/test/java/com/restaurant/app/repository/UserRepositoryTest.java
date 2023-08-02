package com.restaurant.app.repository;

import com.restaurant.app.entities.User;
import com.restaurant.app.repos.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    public void userSaveCheck() {

        User user = new User();
        user.setEmail("bilal4@gmail.com");
        user.setPassword("123");
        user.setRole("user");

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(savedUser.getRole()).isEqualTo(user.getRole());
    }

    // Database user table should be empty
  /*  @Test
    public void getAllUsers() {
        User user1 = new User();
        user1.setEmail("user1@gmail.com");
        user1.setPassword("123");
        user1.setRole("user");
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("user2@gmail.com");
        user2.setPassword("456");
        user2.setRole("user");
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).contains(user1, user2);
    }

   */
    @Test
    public void deleteUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("123");
        user.setRole("admin");
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        userRepository.deleteById(userId);

        Optional<User> deletedUser = userRepository.findById(userId);
        assertThat(deletedUser).isEmpty();
    }



}
