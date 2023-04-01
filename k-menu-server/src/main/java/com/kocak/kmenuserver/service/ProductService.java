package com.kocak.kmenuserver.service;

import com.kocak.kmenuserver.model.MenuItem;
import com.kocak.kmenuserver.repository.ProductRepository;
import com.kocak.kmenuserver.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    public MenuItem addMenuItem(MenuItem menuItem){
       return productRepository.save(menuItem);
    }
    public Optional<List<MenuItem>> getMenuItemsByRestaurantId(String restaurantId){

        return productRepository.findByRestaurant(restaurantRepository.findById(restaurantId).get());
    }
}
