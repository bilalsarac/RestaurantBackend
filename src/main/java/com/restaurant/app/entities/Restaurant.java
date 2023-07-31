package com.restaurant.app.entities;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="restaurant")
@Data
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String category;

    String address;

    @Lob
    String photoUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;

    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;

    private double tasteAverage;
    private double serviceAverage;
    private double priceAverage;


}
