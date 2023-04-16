package com.kocak.kmenuserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@javax.persistence.Table(name = "tables")
@Getter
@Setter
public class Table {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;

    private String restaurantId;

    private String name;

    private boolean active;

    // other fields and their getters/setters

}