package com.kocak.kmenuserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kocak.kmenuserver.dto.MenuCategoryDTO;
import com.kocak.kmenuserver.dto.RestaurantInfoDTO;
import com.kocak.kmenuserver.model.Floor;
import com.kocak.kmenuserver.model.MenuCategory;
import com.kocak.kmenuserver.service.ImageService;
import com.kocak.kmenuserver.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kocak.kmenuserver.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.kocak.kmenuserver.model.Restaurant;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantInfoController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    ImageService imageService;


    @GetMapping("/public/{id}")
    public ResponseEntity<RestaurantInfoDTO> getRestaurantInfo(@PathVariable String id) {
        Optional<Restaurant> restaurantOpt = restaurantService.getRestaurantById(id);

        if (restaurantOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Restaurant restaurant = restaurantOpt.get();
        RestaurantInfoDTO restaurantInfoDTO = new RestaurantInfoDTO();
        restaurantInfoDTO.setId(restaurant.getId());
        restaurantInfoDTO.setName(restaurant.getName());
        restaurantInfoDTO.setContact(restaurant.getContactInfo());
        restaurantInfoDTO.setBackgroundImgUrl(restaurant.getBackgroundImgUrl());
        return ResponseEntity.ok(restaurantInfoDTO);
    }
    @GetMapping("/public/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) throws IOException {
        // id'ye göre resim dosyası seçimi yapın
        byte[] image = imageService.getImageFile(id);

        // eğer resim dosyası yoksa, 404 hata kodu döndürün
        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        // resim dosyasını HTTP yanıtına ekleyin ve yanıtı döndürün
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }


    @PostMapping("/{restaurantId}/floors")
    public ResponseEntity<Floor> createFloor(@PathVariable String restaurantId, @RequestBody Floor floor) {
        Floor createdFloor = restaurantService.createFloor(restaurantId, floor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFloor);
    }
    @PostMapping(value = "/addRestaurant", consumes = { "multipart/form-data" })
    public ResponseEntity<User> createRestaurant(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("restaurantInfo") String restaurantInfoJson) throws JsonProcessingException {
        RestaurantInfoDTO restaurantInfoDTO = new ObjectMapper().readValue(restaurantInfoJson, RestaurantInfoDTO.class);
        Restaurant restaurant;
        try {
            restaurant = restaurantService.createRestaurant(restaurantInfoDTO, file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        User createdUser = restaurantService.addUserToRestaurant(restaurantInfoDTO.getAdminUserInfo(), restaurant.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        Optional<User> userOpt = restaurantService.getUserById(id);

        return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("addCategory")
    public ResponseEntity<MenuCategory> addCategory(@RequestBody MenuCategoryDTO menuCategoryDTO) {
        Optional<MenuCategory> optionalMenuCategory = restaurantService.createCategory(menuCategoryDTO);

        return optionalMenuCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
    }
    @PostMapping("editCategory/{id}")
    public ResponseEntity<MenuCategory> editCategory(@PathVariable String id, @RequestBody MenuCategoryDTO menuCategoryDTO) {
        Optional<MenuCategory> optionalMenuCategory = restaurantService.editCategory(id, menuCategoryDTO);

        return optionalMenuCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
    }

    @PostMapping("deleteCategory/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        boolean deleted = restaurantService.deleteCategory(id);

        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PostMapping("getCategory/{id}")
    public ResponseEntity<List<MenuCategory>> getCategoryByRestaurantId(@PathVariable String id) {
        Optional<List<MenuCategory>> optionalMenuCategory = restaurantService.getAllCategoryByRestaurantId(id);

        return optionalMenuCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }



}
