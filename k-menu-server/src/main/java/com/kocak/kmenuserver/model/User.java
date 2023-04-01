package com.kocak.kmenuserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@javax.persistence.Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    private String id;

    @ManyToMany
    @JoinTable(
            name = "users_restaurants",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> restaurants = new ArrayList<>();

    @Column(name = "max_restaurant_count")
    private int maxRestaurantCount;

    public User(String id, Restaurant restaurant) {
        this.id = id;
        this.restaurants.add(restaurant);
    }

    public User(String id, List<Restaurant> restaurants, int maxRestaurantCount) {
        this.id = id;
        this.restaurants = restaurants;
        this.maxRestaurantCount = maxRestaurantCount;
    }

    public User() {

    }

    public void addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
    }
}
