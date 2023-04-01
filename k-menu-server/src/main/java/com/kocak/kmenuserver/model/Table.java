package com.kocak.kmenuserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    private boolean active;

    // other fields and their getters/setters

}