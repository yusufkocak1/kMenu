package com.kocak.kmenuserver.service;
import com.kocak.kmenuserver.dto.MenuCategoryDTO;
import com.kocak.kmenuserver.dto.RestaurantInfoDTO;
import com.kocak.kmenuserver.exception.FloorNotFoundException;
import com.kocak.kmenuserver.exception.RestaurantNotFoundException;
import com.kocak.kmenuserver.model.*;
import com.kocak.kmenuserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }

    public Restaurant createRestaurant(RestaurantInfoDTO restaurantInfoDTO, MultipartFile file) throws IOException {
        String imageUrl = uploadImage(file);
        Restaurant restaurant = new Restaurant(restaurantInfoDTO.getId(),restaurantInfoDTO.getName(),restaurantInfoDTO.getContact(),restaurantInfoDTO.getAdminUserInfo(),imageUrl);
        restaurant.setBackgroundImgUrl(imageUrl);
        return restaurantRepository.save(restaurant);
    }

    public Floor createFloor(String restaurantId, Floor floor) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            floor.setRestaurant(restaurant.get());
            return floorRepository.save(floor);
        } else {
            throw new RestaurantNotFoundException(restaurantId);
        }
    }

    public Table createTable(Long floorId, Table table) {
        Optional<Floor> floor = floorRepository.findById(floorId);
        if (floor.isPresent()) {
            table.setFloor(floor.get());
            return tableRepository.save(table);
        } else {
            throw new FloorNotFoundException(floorId);
        }
    }
    public Optional<MenuCategory> createCategory(MenuCategoryDTO menuCategoryDTO) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(menuCategoryDTO.getRestaurantId());
        if (restaurant.isPresent()) {
            MenuCategory menuCategory = new MenuCategory(UUID.randomUUID().toString(),restaurant.get(),menuCategoryDTO.getName());

            return Optional.of(categoryRepository.save(menuCategory));
        } else {
            throw new RestaurantNotFoundException(menuCategoryDTO.getRestaurantId());
        }
    }
    public Optional<MenuCategory> editCategory(String id, MenuCategoryDTO menuCategoryDTO) {
        Optional<MenuCategory> oldCategory = categoryRepository.findById(id);
        if (oldCategory.isPresent() && oldCategory.get().getRestaurant().getId().equals(menuCategoryDTO.getRestaurantId())) {
            MenuCategory menuCategory = new MenuCategory(oldCategory.get().getId(),oldCategory.get().getRestaurant(), menuCategoryDTO.getName());
            return Optional.of(categoryRepository.save(menuCategory));
        } else {
            throw new RestaurantNotFoundException(menuCategoryDTO.getRestaurantId());
        }
    }
    public boolean deleteCategory(String id) {

        Optional<MenuCategory> optionalMenuCategory = categoryRepository.findById(id);

        if (optionalMenuCategory.isPresent()) {
            categoryRepository.delete(optionalMenuCategory.get());
            return true;
        } else {
            return false;
        }
    }public Optional<List<MenuCategory>> getAllCategoryByRestaurantId(String id) {

        return categoryRepository.findByRestaurant(restaurantRepository.findById(id).get());
    }


    public User addUserToRestaurant(String userId, String restaurantId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);

        if (userOptional.isEmpty()) {
            User newUser = new User(userId, restaurantOptional.get());
            userRepository.save(newUser);
        } else {
            User user = userOptional.get();
            int currentRestaurantCount = user.getRestaurants().size();
            int maxRestaurantCount = user.getMaxRestaurantCount();
            if (currentRestaurantCount < maxRestaurantCount) {
                user.getRestaurants().add(restaurantOptional.get());
                userRepository.save(user);
            }
        }
        return userRepository.findById(userId).get();
    }

    public Optional<User> getUserById(String  id) {
        return userRepository.findById(id);
    }
    private String uploadImage(MultipartFile image) throws IOException {
        String filePath="";
            // Resim dosyasını kaydetmek için bir dosya adı oluşturun
            String fileName = UUID.randomUUID().toString() + "." + image.getOriginalFilename().split("\\.")[1];

            // Resim dosyasını kaydetmek için bir yol belirleyin
            String uploadDir = "restaurant-images/";
           filePath = uploadDir + fileName;

            // Dosya sistemine resmi kaydet
            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());

        return filePath;
    }


}

