package com.photoshoot.org.Controller;

import com.photoshoot.org.Entity.InventoryItem;
import com.photoshoot.org.Service.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryItemController {

    @Autowired
    private InventoryItemService inventoryItemService;

    // Create a new inventory item
    @PostMapping("/items")
    public ResponseEntity<InventoryItem> createItem(@RequestBody InventoryItem item) {
        InventoryItem newItem = inventoryItemService.saveItem(item);
        return ResponseEntity.ok(newItem);
    }

    // Get all inventory items
    @GetMapping("/items")
    public ResponseEntity<List<InventoryItem>> getAllItems() {
        List<InventoryItem> items = inventoryItemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // Get a specific inventory item by ID
    @GetMapping("/items/{id}")
    public ResponseEntity<InventoryItem> getItemById(@PathVariable Long id) {
        InventoryItem item = inventoryItemService.getItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update an inventory item by ID
    @PutMapping("/items/{id}")
    public ResponseEntity<InventoryItem> updateItem(@PathVariable Long id, @RequestBody InventoryItem updatedItem) {
        InventoryItem updated = inventoryItemService.updateItem(id, updatedItem);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an inventory item by ID
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> softDeleteItem(@PathVariable Long id) {
        inventoryItemService.softDeleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
