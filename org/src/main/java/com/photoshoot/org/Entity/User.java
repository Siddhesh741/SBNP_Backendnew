package com.photoshoot.org.Entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "userId")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "user_type")
    private String userType;
    
    // New field for soft delete flag
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "module_access", columnDefinition = "TEXT")
    private String moduleAccessJson; // Store module access as JSON

    @Transient
    private Map<String, Boolean> moduleAccess; // Actual map for use in the code

    // Convert the moduleAccess Map to JSON before saving to the database
    @PrePersist
    @PreUpdate
    public void convertMapToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (moduleAccess != null) {
                this.moduleAccessJson = objectMapper.writeValueAsString(moduleAccess);
                System.out.println("Converted Map to JSON: " + this.moduleAccessJson);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostLoad
    public void convertJsonToMap() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (this.moduleAccessJson != null) {
                this.moduleAccess = objectMapper.readValue(this.moduleAccessJson, Map.class);
                System.out.println("Converted JSON to Map: " + this.moduleAccess);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Getters and Setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Map<String, Boolean> getModuleAccess() {
        return moduleAccess;
    }

    public void setModuleAccess(Map<String, Boolean> moduleAccess) {
        this.moduleAccess = moduleAccess;
    }

    public String getModuleAccessJson() {
        return moduleAccessJson;
    }

    public void setModuleAccessJson(String moduleAccessJson) {
        this.moduleAccessJson = moduleAccessJson;
    }
    
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
}