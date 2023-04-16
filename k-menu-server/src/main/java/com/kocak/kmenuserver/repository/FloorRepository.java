package com.kocak.kmenuserver.repository;

import com.kocak.kmenuserver.model.Floor;
import com.kocak.kmenuserver.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FloorRepository extends JpaRepository<Floor, String> {
    Optional<List<Floor>> findByRestaurant(Restaurant restaurant);
}
