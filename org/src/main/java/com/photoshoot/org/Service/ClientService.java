package com.photoshoot.org.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.photoshoot.org.Entity.Client;
import com.razorpay.Order;

/**
 * Interface for client-related operations and payment handling.
 */
@Service
public interface ClientService {

    /** Create a new client. */
    Client createClient(Client client);

    /** Get client details by ID. */
    Client getClientById(Long clientId);

    /** Get all active clients. */
    List<Client> getAllClients();

    /** Update an existing client. */
    Client updateClient(Client client);

    /** Soft delete a client (mark as deleted). */
    void softDeleteClient(Long clientId);

    /** Delete a client permanently (not recommended). */
    void deleteClient(Long clientId);

    /** Check if a booking ID exists in the database. */
    boolean isBookingIdExists(String bookingId);

    /** Get client details by booking ID. */
    Optional<Client> getClientByBookingId(String bookingId);

    /** Get the next available booking ID (incremental). */
    int getNextBookingId();

    /** Create a Razorpay payment order. */
    Order createRazorpayOrder(int amount) throws Exception;

    /** Verify Razorpay payment signature for authenticity. */
    boolean verifyPaymentSignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature);

    /** Generate a new booking ID with a prefix. */
    String generateBookingIdWithPrefix();
}
