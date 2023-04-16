package com.kocak.kmenuserver.service;
import com.kocak.kmenuserver.dto.FloorDTO;
import com.kocak.kmenuserver.dto.MenuCategoryDTO;
import com.kocak.kmenuserver.dto.RestaurantInfoDTO;
import com.kocak.kmenuserver.dto.TableDto;
import com.kocak.kmenuserver.exception.FloorNotFoundException;
import com.kocak.kmenuserver.exception.RestaurantNotFoundException;
import com.kocak.kmenuserver.model.*;
import com.kocak.kmenuserver.repository.*;
import org.hibernate.annotations.Tables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public Floor createFloor(FloorDTO floorDTO) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(floorDTO.getRestaurantId());
        if (restaurant.isPresent()) {
            Floor floor= new Floor(UUID.randomUUID().toString(),restaurant.get(),floorDTO.getName());
            floor.setRestaurant(restaurant.get());
            return floorRepository.save(floor);
        } else {
            throw new RestaurantNotFoundException(floorDTO.getRestaurantId());
        }
    }

    public Table createTable(TableDto tableDto) {
        Optional<Floor> floor = floorRepository.findById(tableDto.getFloorId());
        Optional<Restaurant> restaurant = restaurantRepository.findById(tableDto.getRestaurantId());

        if (floor.isPresent() && restaurant.isPresent()) {
            Table table=  new Table();
            table.setFloor(floor.get());
            table.setRestaurantId(tableDto.getRestaurantId());
            table.setName(tableDto.getName());
            table.setId(UUID.randomUUID().toString());
            return tableRepository.save(table);
        } else {
            if(floor.isEmpty()){
                throw new FloorNotFoundException(tableDto.getFloorId());
            } else{
                throw new RestaurantNotFoundException(tableDto.getRestaurantId());
            }
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

        return fileName;
    }


    public Optional<List<Floor>> getFloorsByRestaurantId(String restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException( restaurantId));
        Optional<List<Floor>> floors = floorRepository.findByRestaurant(restaurant);
        if (floors.isEmpty()) {
            throw new FloorNotFoundException();
        }
        return floors;
    }
    public Optional<List<Table>> getTablesByRestaurantId(String restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException( restaurantId));
        Optional<List<Table>> tables = tableRepository.findByRestaurantId(restaurantId);
        if (tables.isEmpty()) {
            throw new FloorNotFoundException();
        }
        return tables;
    }

}

