package com.id.plant.repository;

import com.id.plant.model.Reminder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReminderRepository extends MongoRepository<Reminder, String> {

    // Find all reminders for a specific user
    List<Reminder> findByUserId(String userId);

    // Find all reminders for a specific plant
    List<Reminder> findByPlantId(String plantId);

    // Find upcoming reminders for a user (could be sorted by reminderTime)
    List<Reminder> findByUserIdOrderByReminderTimeAsc(String userId);
}
