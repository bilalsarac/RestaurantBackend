package com.restaurant.app.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name="score",  uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "restaurant_id"}))
@Data
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Min(1)
    @Max(10)
    double serviceScore;

    @Min(1)
    @Max(10)
    double tasteScore;

    @Min(1)
    @Max(10)
    double priceScore;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
