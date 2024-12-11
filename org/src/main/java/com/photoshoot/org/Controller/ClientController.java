package com.photoshoot.org.Controller;

import com.photoshoot.org.Entity.Client;
import com.photoshoot.org.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling client-related operations.
 */
@RestController
@RequestMapping("api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * Create a new client entry.
     *
     * @param client Client object to create.
     * @return ResponseEntity containing created client or error message.
     */
    @PostMapping("/createClient")
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        try {
            // Validate booking ID format
            if (client.getBookingId() != null && !client.getBookingId().startsWith("SBNP-")) {
                return new ResponseEntity<>("Invalid booking ID format", HttpStatus.BAD_REQUEST);
            }

            // Generate booking ID if not present
            if (client.getBookingId() == null) {
                String bookingId = clientService.generateBookingIdWithPrefix();
                client.setBookingId(bookingId);
            }

            Client savedClient = clientService.createClient(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create client: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve a client by booking ID.
     *
     * @param bookingId Booking ID of the client.
     * @return ResponseEntity containing client details or NOT_FOUND status.
     */
    @GetMapping("/bybookingId/{bookingId}")
    public ResponseEntity<Client> getClientByBookingId(@PathVariable("bookingId") String bookingId) {
        try {
            Optional<Client> client = clientService.getClientByBookingId(bookingId);
            return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve all clients.
     *
     * @return List of all active clients.
     */
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = clientService.getAllClients();
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing client.
     *
     * @param clientId ID of the client to update.
     * @param client   Updated client details.
     * @return ResponseEntity containing the updated client.
     */
    @PutMapping("{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") Long clientId, @RequestBody Client client) {
        try {
            client.setId(clientId);
            Client updatedClient = clientService.updateClient(client);
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Soft delete a client by marking them as deleted.
     *
     * @param clientId ID of the client to soft delete.
     * @return ResponseEntity with success message.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") Long clientId) {
        try {
            clientService.softDeleteClient(clientId);
            return new ResponseEntity<>("Client successfully marked as deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete client: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
