package com.kocak.kmenuserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "menu_items")
@Getter
@Setter
public class MenuItem {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private MenuCategory category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String price;
    private boolean active;

    // other fields and their getters/setters

}