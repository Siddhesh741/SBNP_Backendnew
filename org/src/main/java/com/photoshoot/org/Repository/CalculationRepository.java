package com.photoshoot.org.Repository;

import com.photoshoot.org.Entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Repository interface for interacting with the database.
@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long> {

    // Retrieves calculations by date and submission status.
    List<Calculation> findByDateAndSubmitted(String date, String submitted);

    // Finds a calculation by its booking ID.
    Optional<Calculation> findByBookingId(String bookingId);

    // Retrieves calculations by date.
    List<Calculation> findByDate(String date);

    // Retrieves calculations by their submission status.
    List<Calculation> findBySubmitted(String submitted);
}
