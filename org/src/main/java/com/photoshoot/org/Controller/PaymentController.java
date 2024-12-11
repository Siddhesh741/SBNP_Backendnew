package com.photoshoot.org.Controller;

import com.photoshoot.org.Entity.Calculation;
import com.photoshoot.org.Entity.Payment;
import com.photoshoot.org.Repository.CalculationRepository;
import com.photoshoot.org.Service.CalculationService;
import com.photoshoot.org.Service.PaymentService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.savePayment(payment);
        return ResponseEntity.ok(savedPayment);
    }
    
    @GetMapping("/by-date")
    public ResponseEntity<List<Payment>> getPaymentsByDate(@RequestParam String date) {
        List<Payment> payments = paymentService.getPaymentsByDate(date);
        return ResponseEntity.ok(payments);
    }


}
