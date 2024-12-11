package com.photoshoot.org.Repository;

import com.photoshoot.org.Entity.Calculation;
import com.photoshoot.org.Entity.Payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	List<Calculation> findByisSubmitted(String isSubmitted);
	 List<Payment> findBySubmittedBy(String submittedBy); 
	 List<Payment> findByIsSubmitted(String isSubmitted);
	 List<Payment> findByDate(String date);

}
