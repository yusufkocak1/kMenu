package com.kocak.kmenuserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kocak.kmenuserver.dto.FloorDTO;
import com.kocak.kmenuserver.dto.MenuCategoryDTO;
import com.kocak.kmenuserver.dto.RestaurantInfoDTO;
import com.kocak.kmenuserver.dto.TableDto;
import com.kocak.kmenuserver.model.*;
import com.kocak.kmenuserver.service.ImageService;
import com.kocak.kmenuserver.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/public/image/{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable String name) throws IOException {
        String[] parts = name.split("\\.");
        String extension = parts[parts.length - 1];
        String mediaType;
        switch (extension) {
            case "png":
                mediaType = MediaType.IMAGE_PNG_VALUE;
                break;
            case "jpg":
            case "jpeg":
                mediaType = MediaType.IMAGE_JPEG_VALUE;
                break;
            case "gif":
                mediaType = MediaType.IMAGE_GIF_VALUE;
                break;
            default:
                mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
                break;
        }
        byte[] image = imageService.getImageFile(name);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mediaType)).body(image);
    }


    @PostMapping("/addFloor")
    public ResponseEntity<Floor> createFloor( @RequestBody FloorDTO floor) {
        Floor createdFloor = restaurantService.createFloor(floor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFloor);
    }
    @GetMapping("/getFloors/{restaurantId}")
    public ResponseEntity<List<Floor>> getFloorsByRestaurantId(@PathVariable("restaurantId") String restaurantId){
        Optional<List<Floor>> optionalFloors = restaurantService.getFloorsByRestaurantId(restaurantId);
        return     optionalFloors.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
    @PostMapping("/addTables")
    public ResponseEntity<Table> createTable( @RequestBody TableDto tableDto) {
        Table createdFloor = restaurantService.createTable(tableDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFloor);
    }
    @GetMapping("/getTables/{restaurantId}")
    public ResponseEntity<List<Table>> getTablesByRestaurantId(@PathVariable("restaurantId") String restaurantId){
        Optional<List<Table>> optionalTables = restaurantService.getTablesByRestaurantId(restaurantId);
        return     optionalTables.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

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
