package com.restaurant.app.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comment",  uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "restaurant_id"}))
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    Restaurant restaurant;

    String text;

    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;

}
