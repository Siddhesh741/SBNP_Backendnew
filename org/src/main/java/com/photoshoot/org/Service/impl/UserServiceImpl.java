package com.photoshoot.org.Service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photoshoot.org.Entity.User;
import com.photoshoot.org.Repository.UserRepository;
import com.photoshoot.org.Service.UserService;

import jakarta.transaction.Transactional;

/**
 * Implementation of UserService for managing user operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** Create a new user. */
    @Override
    public User createUser(User user) {
        System.out.println("Creating user with module access: " + user.getModuleAccess());
        return userRepository.save(user);
    }

    /** Get user details by ID (only non-deleted users). */
    @Override
    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.filter(user -> !user.getIsDeleted()).orElse(null);
    }

    /** Get user details by their username (userId). */
    @Override
    public Optional<User> getUserByUserId(String userId) {
        return userRepository.findByUserIdAndIsDeletedFalse(userId);
    }

    /** Get all active users. */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findByIsDeletedFalse();
    }

    /** Authenticate a user by username and password. */
    @Override
    public Optional<User> authenticateUser(String userId, String password) {
        return userRepository.findByUserIdAndPassword(userId, password);
    }

    /** Soft delete a user (mark as deleted). */
    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setIsDeleted(true);
            userRepository.save(user);
        });
    }

    /** Update an existing user. */
    @Override
    public User updateUser(User user) {
        return userRepository.findById(user.getId()).map(existingUser -> {
            existingUser.setFullName(user.getFullName());
            existingUser.setMobile(user.getMobile());
            existingUser.setUserId(user.getUserId());
            existingUser.setPassword(user.getPassword());
            existingUser.setUserType(user.getUserType());

            if (user.getModuleAccess() != null) {
                existingUser.setModuleAccess(user.getModuleAccess());
                existingUser.convertMapToJson(); // Convert to JSON for persistence
            }
            System.out.println("Updating user: " + existingUser);
            return userRepository.save(existingUser);
        }).orElse(null);
    }

    /**
     * Update module access permissions for a user.
     * This method is transactional to ensure atomic updates.
     */
    @Transactional
    @Override
    public User updateModuleAccess(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setModuleAccess(updatedUser.getModuleAccess());
            existingUser.convertMapToJson();
            return userRepository.save(existingUser);
        }).orElse(null);
    }
}
