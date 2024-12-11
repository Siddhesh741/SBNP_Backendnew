package com.photoshoot.org.Service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.photoshoot.org.Entity.Client;
import com.photoshoot.org.Repository.ClientRepository;
import com.photoshoot.org.Service.ClientService;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ClientService interface to handle client-related operations and payment integration.
 */
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final String razorpayKey;
    private final String razorpaySecret;
    private final RazorpayClient razorpayClient;

    /**
     * Constructor for dependency injection.
     */
    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             @Value("${razorpay.key.id}") String razorpayKey,
                             @Value("${razorpay.secret.key}") String razorpaySecret) throws Exception {
        this.clientRepository = clientRepository;
        this.razorpayKey = razorpayKey;
        this.razorpaySecret = razorpaySecret;
        this.razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);
    }

    /**
     * Creates a Razorpay order for payment processing.
     */
    @Override
    public Order createRazorpayOrder(int amount) throws Exception {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // Amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + getNextBookingId());
        return razorpayClient.orders.create(orderRequest);
    }

    /**
     * Verifies the payment signature to ensure the transaction's integrity.
     */
    @Override
    public boolean verifyPaymentSignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", razorpayOrderId);
            options.put("razorpay_payment_id", razorpayPaymentId);
            options.put("razorpay_signature", razorpaySignature);
            return Utils.verifyPaymentSignature(options, razorpaySecret);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates a new client entry.
     */
    @Transactional
    @Override
    public Client createClient(Client client) {
        if (client.getCreatedBy() == null || client.getCreatedBy().isEmpty()) {
            throw new IllegalArgumentException("CreatedBy field is required.");
        }
        return clientRepository.save(client);
    }

    /**
     * Retrieves a client by its ID.
     */
    @Override
    public Client getClientById(Long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        return optionalClient.orElse(null);
    }

    /**
     * Retrieves a client by its booking ID.
     */
    @Override
    public Optional<Client> getClientByBookingId(String bookingId) {
        return clientRepository.findActiveByBookingId(bookingId);
    }

    /**
     * Retrieves all active clients from the database.
     */
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAllActiveClients();
    }

    /**
     * Updates an existing client's details.
     */
    @Transactional
    @Override
    public Client updateClient(Client client) {
        Client existingClient = clientRepository.findById(client.getId()).orElse(null);
        if (existingClient != null) {
            existingClient.setclientName(client.getclientName());
            existingClient.setclientPhoneNo(client.getclientPhoneNo());
            existingClient.setclientEmail(client.getclientEmail());
            existingClient.setphotographerName(client.getphotographerName());
            existingClient.setphotographerPhoneNo(client.getphotographerPhoneNo());
            existingClient.setphotographerEmail(client.getphotographerEmail());
            existingClient.setselectDate(client.getselectDate());
            existingClient.setselectedPackage(client.getselectedPackage());
            existingClient.setpackageAmount(client.getpackageAmount());
            existingClient.setactualAmount(client.getactualAmount());
            existingClient.setvisitorsCount(client.getvisitorsCount());
            existingClient.setcashcollectedby(client.getcashcollectedby());
            existingClient.setpaymentMode(client.getpaymentMode());
            existingClient.setcityName(client.getcityName());
            existingClient.setknowaboutlocation(client.getknowaboutlocation());
            existingClient.setagreeTerms(client.getagreeTerms());
            existingClient.setEditedBy(client.getEditedBy());
            return clientRepository.save(existingClient);
        }
        throw new IllegalArgumentException("Client not found with ID: " + client.getId());
    }

    /**
     * Marks a client as soft deleted.
     */
    @Transactional
    @Override
    public void softDeleteClient(Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setDeleted(true); // Mark as deleted
            clientRepository.save(client);
        } else {
            throw new IllegalArgumentException("Client not found with ID: " + clientId);
        }
    }

    /**
     * Disables direct deletion of a client to enforce soft delete.
     */
    @Override
    public void deleteClient(Long clientId) {
        throw new UnsupportedOperationException("Direct deletion is not allowed. Use softDeleteClient instead.");
    }

    /**
     * Checks if a booking ID exists in the database.
     */
    @Override
    public boolean isBookingIdExists(String bookingId) {
        return clientRepository.existsByBookingId(bookingId);
    }

    /**
     * Generates the next booking ID by incrementing the current maximum.
     */
    @Override
    public int getNextBookingId() {
        Integer maxId = clientRepository.findMaxBookingId(); // Query to find the max booking ID
        return (maxId == null) ? 1 : maxId + 1;
    }

    /**
     * Generates a new booking ID with a specific prefix.
     */
    public String generateBookingIdWithPrefix() {
        int nextId = getNextBookingId();
        return String.format("SBNP-%06d", nextId); // Add prefix and zero-padding
    }
}
