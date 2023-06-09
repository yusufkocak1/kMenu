package com.kocak.kmenuserver.dto;

import org.springframework.web.multipart.MultipartFile;

public class RestaurantInfoDTO {
    private String id;
    private String name;
    private String contact;
    private String adminUserInfo;


    public RestaurantInfoDTO(String name, String contact, String adminUserInfo ) {
        this.name = name;
        this.contact = contact;
        this.adminUserInfo = adminUserInfo;
    }

    public RestaurantInfoDTO() {

    }

    public RestaurantInfoDTO(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdminUserInfo() {
        return adminUserInfo;
    }

    public void setAdminUserInfo(String adminUserInfo) {
        this.adminUserInfo = adminUserInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
