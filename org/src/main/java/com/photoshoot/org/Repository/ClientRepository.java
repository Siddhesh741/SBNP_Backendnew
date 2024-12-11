package com.photoshoot.org.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.photoshoot.org.Entity.Client;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing database operations on Client.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

	 // Check if booking ID exists
    boolean existsByBookingId(String bookingId);

    // Find a client by booking ID
    Optional<Client> findByBookingId(String bookingId);

    // Find all active (not deleted) clients
    @Query("SELECT c FROM Client c WHERE c.isDeleted = false")
    List<Client> findAllActiveClients();

    // Find active client by booking ID
    @Query("SELECT c FROM Client c WHERE c.bookingId = :bookingId AND c.isDeleted = false")
    Optional<Client> findActiveByBookingId(String bookingId);

    // Find the highest booking ID
    @Query("SELECT MAX(CAST(SUBSTRING(c.bookingId, 6) AS int)) FROM Client c WHERE c.bookingId LIKE 'SBNP-%'")
    Integer findMaxBookingId();
}
