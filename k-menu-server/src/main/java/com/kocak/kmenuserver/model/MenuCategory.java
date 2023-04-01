package com.kocak.kmenuserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "menu_categories")
@Getter
@Setter
public class MenuCategory {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String name;

    // other fields and their getters/setters


    public MenuCategory(String id, Restaurant restaurant, String name) {
        this.id = id;
        this.restaurant = restaurant;
        this.name = name;
    }

    public MenuCategory() {

    }
}