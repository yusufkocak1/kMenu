package com.kocak.kmenuserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "restaurants")
@Getter
@Setter
public class Restaurant {

    @Id
    private String id;

    private String name;

    private String contactInfo;

    private String adminUserInfo;

    private String backgroundImgUrl;

    // constructors, getters and setters, toString


    public Restaurant(String id,String name, String contactInfo, String adminUserInfo, String backgroundImgUrl) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.adminUserInfo = adminUserInfo;
        this.backgroundImgUrl = backgroundImgUrl;
    }

    public Restaurant() {

    }
}
