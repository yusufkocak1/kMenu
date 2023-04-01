package com.kocak.kmenuserver.repository;

import com.kocak.kmenuserver.model.MenuCategory;
import com.kocak.kmenuserver.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<MenuCategory,String> {
    Optional<List<MenuCategory>> findByRestaurant(Restaurant restaurant);
}
