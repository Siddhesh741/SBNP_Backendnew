package com.photoshoot.org.Service;

import com.photoshoot.org.Entity.Calculation;

import java.util.List;

// Service interface defining core calculation operations.
public interface CalculationService {

    // Saves a new calculation entry after validation and processing.
    Calculation calculateAndSave(Calculation calculation);

    // Retrieves calculations filtered by a specific date.
    List<Calculation> getCalculationsByDate(String date);

    // Updates an existing calculation entry based on the booking ID.
    Calculation updateCalculation(String id, Calculation calculation);

    // Checks if a booking ID has already been submitted.
    boolean isBookingIdSubmitted(String bookingId);

    // Retrieves all calculation entries.
    List<Calculation> getAllCalculations();
}
