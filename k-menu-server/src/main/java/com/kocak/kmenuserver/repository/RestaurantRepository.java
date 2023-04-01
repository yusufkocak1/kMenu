package com.kocak.kmenuserver.repository;

import com.kocak.kmenuserver.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
    // Custom query methods can be defined here if needed
}
