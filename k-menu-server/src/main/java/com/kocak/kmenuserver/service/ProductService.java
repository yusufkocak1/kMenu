package com.kocak.kmenuserver.service;

import com.kocak.kmenuserver.dto.MenuItemDTO;
import com.kocak.kmenuserver.model.MenuItem;
import com.kocak.kmenuserver.repository.CategoryRepository;
import com.kocak.kmenuserver.repository.ProductRepository;
import com.kocak.kmenuserver.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    CategoryRepository categoryRepository;
    public MenuItem addMenuItem(MenuItemDTO menuItemDTO){
        MenuItem menuItem = new MenuItem();
        menuItem.setId(UUID.randomUUID().toString());
        menuItem.setCategory(categoryRepository.findById(menuItemDTO.getCategory()).get());
        menuItem.setRestaurant(restaurantRepository.findById(menuItemDTO.getRestaurant()).get());
        menuItem.setName(menuItemDTO.getName());
        menuItem.setPrice(menuItemDTO.getPrice());
        menuItem.setActive(menuItemDTO.isActive());
       return productRepository.save(menuItem);
    }
    public Optional<List<MenuItem>> getMenuItemsByRestaurantId(String restaurantId){

        return productRepository.findByRestaurant(restaurantRepository.findById(restaurantId).get());
    }
}
