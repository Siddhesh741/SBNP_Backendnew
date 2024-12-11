package com.photoshoot.org.Service;

import java.util.List;
import java.util.Optional;

import com.photoshoot.org.Entity.User;

/**
 * Service interface for managing user operations.
 */
public interface UserService {

    /** Create a new user. */
    User createUser(User user);

    /** Get user details by ID. */
    User getUserById(Long userId);

    /** Get user details by their username (userId). */
    Optional<User> getUserByUserId(String userId);

    /** Authenticate a user by username and password. */
    Optional<User> authenticateUser(String userId, String password);

    /** Get all active users. */
    List<User> getAllUsers();

    /** Update an existing user. */
    User updateUser(User user);

    /** Update module access permissions for a user. */
    User updateModuleAccess(Long id, User updatedUser);

    /** Soft delete a user (mark as deleted). */
    void deleteUser(Long id);
}
