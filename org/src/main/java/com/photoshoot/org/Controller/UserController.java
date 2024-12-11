package com.photoshoot.org.Controller;

import com.photoshoot.org.Entity.User;
import com.photoshoot.org.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Create a new user
     * @param user User object received from the request body
     * @return ResponseEntity with the created user
     */
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        System.out.println("Created User ID: " + savedUser.getId());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /**
     * Fetch all users from the database
     * @return List of all active users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Update user details
     * @param userId ID of the user to be updated
     * @param user Updated user object from the request body
     * @return ResponseEntity with the updated user details
     */
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
        user.setId(userId);
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Soft delete a user by setting `isDeleted` to true
     * @param id ID of the user to be deleted
     * @return ResponseEntity indicating success or failure
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }

    /**
     * Authenticate a user by verifying username and password
     * @param user User object containing userId and password
     * @return ResponseEntity with user details or error message
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        // Check if the username exists
        Optional<User> existingUser = userService.getUserByUserId(user.getUserId());
        if (!existingUser.isPresent()) {
            // Return error response for invalid username
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid username"));
        }

        // Check if the password matches
        User foundUser = existingUser.get();
        if (!foundUser.getPassword().equals(user.getPassword())) {
            // Return error response for invalid password
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid password"));
        }

        // Authentication successful
        return ResponseEntity.ok(foundUser);
    }

    /**
     * Fetch user details by userId
     * @param userId User ID to search for
     * @return ResponseEntity with user details or NOT_FOUND
     */
    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<User> getUserByUserId(@PathVariable("userId") String userId) {
        Optional<User> user = userService.getUserByUserId(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update module access for a user
     * @param userId ID of the user to update
     * @param user User object containing updated module access
     * @return ResponseEntity with the updated user details
     */
    @PutMapping("/updateAccess/{id}")
    public ResponseEntity<User> updateModuleAccess(@PathVariable("id") Long userId, @RequestBody User user) {
        User updatedUser = userService.updateModuleAccess(userId, user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
