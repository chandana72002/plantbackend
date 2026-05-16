package com.id.plant.controller;

import com.id.plant.model.Plant;
import com.id.plant.model.Reminder;
import com.id.plant.model.User;
import com.id.plant.repository.PlantRepository;
import com.id.plant.repository.ReminderRepository;
import com.id.plant.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    // ================= USER PROFILE =================
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable String id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable String id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ================= USER PLANTS =================
    // Fetch ALL plants (admin-added + user-added)
    @GetMapping("/plants")
    public ResponseEntity<List<Plant>> getAllPlants() {
        return ResponseEntity.ok(plantRepository.findAll());
    }

    // Add a plant for a specific user
    @PostMapping("/plants")
    public ResponseEntity<Plant> addUserPlant(@PathVariable String id, @RequestBody Plant plant) {
        plant.setUserId(id);
        if (plant.getCreatedAt() == null) {
            plant.setCreatedAt(LocalDateTime.now());
        }
        return ResponseEntity.ok(plantRepository.save(plant));
    }
    
    @PutMapping("/plants/{plantId}/bought")
    public Plant updateBoughtStatus(@PathVariable String plantId, @RequestBody Map<String, Boolean> body) {
        Plant plant = plantRepository.findById(plantId).orElseThrow();
        plant.setBought(body.get("bought"));
        return plantRepository.save(plant);
    }

    // ================= USER REMINDERS =================
    // Fetch ALL reminders
    @GetMapping("/reminders")
    public ResponseEntity<List<Reminder>> getAllReminders() {
        return ResponseEntity.ok(reminderRepository.findAll());
    }

    // Add a reminder for a specific user
    @PostMapping("/{id}/reminders")
    public ResponseEntity<Reminder> addUserReminder(@PathVariable String id, @RequestBody Reminder reminder) {
        reminder.setUserId(id);
        if (reminder.getCreatedAt() == null) {
            reminder.setCreatedAt(LocalDateTime.now());
        }
        return ResponseEntity.ok(reminderRepository.save(reminder));
    }

    // ================= USER STATS =================
    // Stats for a specific user
    @GetMapping("/{id}/stats")
    public ResponseEntity<?> getUserStats(@PathVariable String id) {
        return userRepository.findById(id).map(user -> {
            List<Plant> plants = plantRepository.findByUserId(id);
            List<Reminder> reminders = reminderRepository.findByUserId(id);

            Map<String, Object> stats = new HashMap<>();
            stats.put("userId", id);
            stats.put("username", user.getUsername());
            stats.put("totalPlants", plants.size());
            stats.put("totalReminders", reminders.size());
            stats.put("plants", plants);
            stats.put("reminders", reminders);

            return ResponseEntity.ok(stats);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ================= GLOBAL STATS =================
    // Stats for all users combined
    @GetMapping("/all/stats")
    public ResponseEntity<?> getAllUserStats() {
        List<Plant> allPlants = plantRepository.findAll();
        List<Reminder> allReminders = reminderRepository.findAll();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalPlants", allPlants.size());
        stats.put("totalReminders", allReminders.size());
        stats.put("plants", allPlants);
        stats.put("reminders", allReminders);

        return ResponseEntity.ok(stats);
    }
}
