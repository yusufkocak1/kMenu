package com.kocak.kmenuserver.dto;


import lombok.Data;

@Data
public class MenuItemDTO {

    private String category;

    private String restaurant;


    private String name;

    private String price;
    private boolean active;

}
