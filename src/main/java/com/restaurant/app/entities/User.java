package com.restaurant.app.entities;


import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @NotNull
    private String password;

    @Column(nullable = true)
    private String role;

    @Lob
    @Column(nullable = true)
    private String photoUrl;

}
