package com.kocak.kmenuserver.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "floors")
@Getter
@Setter
public class Floor {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String name;

    // other fields and their getters/setters

}