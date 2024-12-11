package com.photoshoot.org.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photoshoot.org.Entity.Calculation;
import com.photoshoot.org.Repository.CalculationRepository;
import com.photoshoot.org.Service.CalculationService;

import jakarta.transaction.Transactional;

@Service
public class CalculationServiceImpl implements CalculationService {

    @Autowired
    private CalculationRepository calculationRepository;

    @Override
    @Transactional // Ensure database consistency.
    public Calculation calculateAndSave(Calculation calculation) {
        if (calculation.getActualAmount() == null || calculation.getWhoReceivedMoney() == null) {
            throw new IllegalArgumentException("Actual amount and who received money are required.");
        }

        if (calculation.getSubmittedBy() == null || calculation.getSubmittedBy().isEmpty()) {
            throw new IllegalArgumentException("Admin name (submittedBy) is required.");
        }

        calculation.setSubmitted("S"); // Automatically mark as submitted.
        return recalculate(calculation);
    }

    @Override
    public boolean isBookingIdSubmitted(String bookingId) {
        return calculationRepository.findByBookingId(bookingId).isPresent();
    }

    @Override
    @Transactional // Wrap in a transaction for safe updates.
    public Calculation updateCalculation(String bookingId, Calculation calculation) {
        Calculation existingCalculation = calculationRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Calculation not found"));

        boolean isUpdated = false;

        if (calculation.getActualAmount() != null &&
                !calculation.getActualAmount().equals(existingCalculation.getActualAmount())) {
            existingCalculation.setActualAmount(calculation.getActualAmount());
            isUpdated = true;
        }

        if (calculation.getWhoReceivedMoney() != null &&
                !calculation.getWhoReceivedMoney().equals(existingCalculation.getWhoReceivedMoney())) {
            existingCalculation.setWhoReceivedMoney(calculation.getWhoReceivedMoney());
            isUpdated = true;
        }

        if (calculation.getEditedBy() != null) {
            existingCalculation.setEditedBy(calculation.getEditedBy());
            isUpdated = true;
        }

        if (!isUpdated) {
            return null;
        }

        return recalculate(existingCalculation);
    }

    private Calculation recalculate(Calculation calculation) {
        Integer roomMaintenance = 500;
        Integer bookingAmount = calculation.getActualAmount();
        Integer sbnpBalance = 0, taranginiiBalance = 0;

        if (bookingAmount != null && bookingAmount > 1500) {
            if ("SBNP".equals(calculation.getWhoReceivedMoney())) {
                sbnpBalance = bookingAmount - roomMaintenance;
            } else if ("Taranginii".equals(calculation.getWhoReceivedMoney())) {
                taranginiiBalance = bookingAmount - roomMaintenance;
            }
            calculation.setRoomMaintenance(roomMaintenance);
        } else {
            if ("SBNP".equals(calculation.getWhoReceivedMoney())) {
                sbnpBalance = bookingAmount;
            } else if ("Taranginii".equals(calculation.getWhoReceivedMoney())) {
                taranginiiBalance = bookingAmount;
            }
            calculation.setRoomMaintenance(0);
        }

        sbnpBalance = Math.max(sbnpBalance, 0);
        taranginiiBalance = Math.max(taranginiiBalance, 0);

        calculation.setSbnpBalance(sbnpBalance);
        calculation.setTaranginiiBalance(taranginiiBalance);
        calculation.setTotalIncome(sbnpBalance + taranginiiBalance);

        int sharingAmount = Math.abs(sbnpBalance - taranginiiBalance) / 2;
        if (sbnpBalance > taranginiiBalance) {
            calculation.setSettlementMessage("SBNP needs to pay Taranginii ₹" + sharingAmount);
        } else if (taranginiiBalance > sbnpBalance) {
            calculation.setSettlementMessage("Taranginii needs to pay SBNP ₹" + sharingAmount);
        } else {
            calculation.setSettlementMessage("No payment required.");
        }

        return calculationRepository.save(calculation);
    }

    @Override
    public List<Calculation> getCalculationsByDate(String date) {
        return calculationRepository.findByDate(date);
    }

    @Override
    public List<Calculation> getAllCalculations() {
        return calculationRepository.findAll();
    }
}
