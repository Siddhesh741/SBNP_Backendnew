package com.photoshoot.org.Service;

import com.photoshoot.org.Entity.Calculation;
import com.photoshoot.org.Entity.Payment;
import com.photoshoot.org.Repository.CalculationRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface PaymentService {
    Payment savePayment(Payment payment);
    List<Payment> getPaymentsByDate(String date);

}
