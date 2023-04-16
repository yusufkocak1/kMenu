package com.kocak.kmenuserver.repository;

import com.kocak.kmenuserver.model.Floor;
import com.kocak.kmenuserver.model.Restaurant;
import com.kocak.kmenuserver.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<Table, Long> {
    Optional<List<Table>> findByRestaurantId(String restaurantId);

}
