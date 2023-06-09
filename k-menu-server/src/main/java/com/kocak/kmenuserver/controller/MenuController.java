package com.kocak.kmenuserver.controller;

import com.kocak.kmenuserver.model.MenuItem;
import com.kocak.kmenuserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/restaurants")
public class MenuController {

    @Autowired
    ProductService productService;

    @PostMapping("addProduct")
    public ResponseEntity<MenuItem> addCategory(@RequestBody MenuItem menuItem) {
        Optional<MenuItem> optionalMenuItem = Optional.ofNullable(productService.addMenuItem(menuItem));

        return optionalMenuItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
    }
    @PostMapping("getProducts")
    public ResponseEntity<List<MenuItem>> addCategory(@RequestBody String restaurantId) {
        Optional<List<MenuItem>> optionalMenuCategory = productService.getMenuItemsByRestaurantId(restaurantId);

        return optionalMenuCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
    }
}
