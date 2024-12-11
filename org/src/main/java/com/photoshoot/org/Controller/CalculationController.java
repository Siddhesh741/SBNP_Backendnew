package com.photoshoot.org.Controller;

import com.photoshoot.org.Entity.Calculation;
import com.photoshoot.org.Repository.CalculationRepository;
import com.photoshoot.org.Service.CalculationService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing calculation-related operations.
 */
@RestController
@RequestMapping("/api/calculate")
public class CalculationController {

    @Autowired
    private CalculationService calculationService;

    private final CalculationRepository calculationRepository;

    // Constructor-based injection for better testing and immutability.
    public CalculationController(CalculationRepository calculationRepository) {
        this.calculationRepository = calculationRepository;
    }

    /**
     * Retrieves calculations by date.
     * @param date the date to filter calculations.
     * @return List of calculations matching the date.
     */
    @GetMapping("/by-date")
    public List<Calculation> getCalculationsByDate(@RequestParam String date) {
        return calculationService.getCalculationsByDate(date);
    }

    /**
     * Retrieves booking IDs of all submitted calculations.
     * @return List of booking IDs with submitted status "S".
     */
    @GetMapping("/all-submitted-status")
    public List<String> getAllSubmittedBookingIds() {
        List<Calculation> submittedCalculations = calculationRepository.findBySubmitted("S");
        return submittedCalculations.stream()
                .map(Calculation::getBookingId)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new calculation entry.
     * @param calculation the calculation details provided in the request body.
     * @return The saved calculation entity.
     */
    @PostMapping
    public Calculation calculate(@RequestBody Calculation calculation) {
        if (calculation.getActualAmount() == null || calculation.getWhoReceivedMoney() == null) {
            throw new RuntimeException("Actual amount and who received money are required.");
        }

        if (calculationService.isBookingIdSubmitted(calculation.getBookingId())) {
            throw new RuntimeException("This booking ID has already been submitted.");
        }

        if (calculation.getSubmittedBy() == null || calculation.getSubmittedBy().isEmpty()) {
            throw new RuntimeException("Admin name (submittedBy) is required.");
        }

        Calculation savedCalculation = calculationService.calculateAndSave(calculation);

        // Mark calculation as submitted after saving.
        savedCalculation.setSubmitted("S");
        calculationRepository.save(savedCalculation);

        return savedCalculation;
    }

    /**
     * Updates an existing calculation.
     * @param id the ID of the calculation to update.
     * @param calculation the new calculation details.
     * @return Updated calculation entity.
     */
    @PutMapping("/{id}")
    public Calculation updateCalculation(@PathVariable String id, @RequestBody Calculation calculation) {
        if (calculation.getActualAmount() == null && calculation.getWhoReceivedMoney() == null) {
            throw new RuntimeException("Error: At least one field must be updated.");
        }

        if (calculation.getEditedBy() == null || calculation.getEditedBy().isEmpty()) {
            throw new RuntimeException("Error: Admin name (editedBy) is required.");
        }

        Calculation updatedCalculation = calculationService.updateCalculation(id, calculation);

        if (updatedCalculation != null) {
            return updatedCalculation;
        }

        throw new RuntimeException("Error: Failed to update the calculation.");
    }
}
