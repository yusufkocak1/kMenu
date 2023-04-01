package com.kocak.kmenuserver.repository;

import com.kocak.kmenuserver.model.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
    List<Floor> findByRestaurantId(Long restaurantId);
}
