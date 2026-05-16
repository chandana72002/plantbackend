package com.id.plant.repository;

import com.id.plant.model.Plant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlantRepository extends MongoRepository<Plant, String> {

    // Find all plants by user ID
    List<Plant> findByUserId(String userId);

    // Optional: find plants by type
    List<Plant> findByType(String type);
}
