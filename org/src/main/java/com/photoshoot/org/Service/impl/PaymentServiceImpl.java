package com.photoshoot.org.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photoshoot.org.Entity.Calculation;
import com.photoshoot.org.Entity.Payment;
import com.photoshoot.org.Repository.CalculationRepository;
import com.photoshoot.org.Repository.PaymentRepository;
import com.photoshoot.org.Service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CalculationRepository calculationRepository; //
    @Override
    public Payment savePayment(Payment payment) {
        payment.setIsSubmitted("S"); // Update the flag to "S"
        return paymentRepository.save(payment);
    }

    
    @Override
    public List<Payment> getPaymentsByDate(String date) {
        return paymentRepository.findByDate(date);
    }

    
    // Method to save all fields of a Payment entity
    public Payment savePaymentDetails(String date, double amount, String paymentStatus, 
                                      String paymentMode, String transactionId, 
                                      String comments, String settlementMessage, 
                                      String sender, String receiver, String submittedBy) {
        // Create a new Payment entity
        Payment payment = new Payment();
        payment.setDate(date);
        payment.setAmount(amount);
        payment.setPaymentStatus(paymentStatus);
        payment.setPaymentMode(paymentMode);
        payment.setTransactionId(transactionId);
        payment.setComments(comments);
        payment.setSettlementMessage(settlementMessage);
        payment.setSender(sender);
        payment.setReceiver(receiver);
        payment.setIsSubmitted("S"); // Set the flag to "S"
        payment.setSubmittedBy(submittedBy);
        // Save the entity and return
        return paymentRepository.save(payment);
    }
}
