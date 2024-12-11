package com.photoshoot.org.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.photoshoot.org.Entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /** Check if a user exists by their userId. */
    boolean existsByUserId(String userId);

    /** Find a user by userId (only non-deleted users). */
    Optional<User> findByUserIdAndIsDeletedFalse(String userId);

    /** Find all users who are not marked as deleted. */
    List<User> findByIsDeletedFalse();

    /** Find a user by userId and password (authentication). */
    Optional<User> findByUserIdAndPassword(String userId, String password);
}
