package com.id.plant.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "plants")
public class Plant {

    @Id
    private String id;
    private String name;                
    private String type;                
    private String photoUrl;            
    private String wateringFrequency;   
    private String fertilizingFrequency;
    private String userId;  
    private boolean bought = false;   // ✅ Correct naming

    @CreatedDate
    @Field("createdAt")
    private LocalDateTime createdAt; // Automatically set when inserted

    public Plant() {}

    public Plant(String name, String type, String photoUrl, String wateringFrequency,
                 String fertilizingFrequency, String userId) {
        this.name = name;
        this.type = type;
        this.photoUrl = photoUrl;
        this.wateringFrequency = wateringFrequency;
        this.fertilizingFrequency = fertilizingFrequency;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.bought = false;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(String wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public String getFertilizingFrequency() {
        return fertilizingFrequency;
    }

    public void setFertilizingFrequency(String fertilizingFrequency) {
        this.fertilizingFrequency = fertilizingFrequency;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isBought() {   // ✅ Proper getter
        return bought;
    }

    public void setBought(boolean bought) {  // ✅ Proper setter
        this.bought = bought;
    }
}
