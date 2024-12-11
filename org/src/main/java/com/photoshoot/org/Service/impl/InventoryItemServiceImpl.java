package com.photoshoot.org.Service.impl;

import com.photoshoot.org.Entity.InventoryItem;
import com.photoshoot.org.Repository.InventoryItemRepository;
import com.photoshoot.org.Service.InventoryItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryItemServiceImpl implements InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;

    @Autowired
    public InventoryItemServiceImpl(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @Override
    public InventoryItem saveItem(InventoryItem item) {
        return inventoryItemRepository.save(item);
    }

//    @Override
//    public InventoryItem updateItem(Long id, InventoryItem item) {
//        Optional<InventoryItem> existingItemOptional = inventoryItemRepository.findById(id);
//        if (existingItemOptional.isPresent()) {
//            InventoryItem existingItem = existingItemOptional.get();
//            existingItem.setCreatedBy(item.getCreatedBy());
//            existingItem.setUpdatedBy(item.getUpdatedBy());
//            existingItem.setItemName(item.getItemName());
//            existingItem.setQuantity(item.getQuantity());
//            existingItem.setcomment(item.getcomment());
//            existingItem.setDate(item.getDate());
//            return inventoryItemRepository.save(existingItem);
//        } else {
//            throw new RuntimeException("Item not found with id: " + id);
//        }
//    }

    
    public InventoryItem updateItem(Long id, InventoryItem updatedItem) {
        InventoryItem item = inventoryItemRepository.findById(id).orElse(null);
        if (item != null) {
            // Update fields based on the provided updatedItem data
            item.setQuantity(updatedItem.getQuantity());
            item.setUpdatedBy(updatedItem.getUpdatedBy() != null ? updatedItem.getUpdatedBy() : item.getCreatedBy());
            item.setcomment(updatedItem.getcomment());
            item.setDate(updatedItem.getDate());
            item.setCreatedBy(item.getCreatedBy());
            // Save the updated item back to the database
            return inventoryItemRepository.save(item);
        }
        return null; // Return null or handle the case when the item does not exist
    }


    
    @Override
    public void deleteItem(Long id) {
        inventoryItemRepository.deleteById(id);
    }
    
    @Override
    public void softDeleteItem(Long id) {
        InventoryItem item = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        item.setIsDeleted(true); // Set isDeleted to true for soft delete
        inventoryItemRepository.save(item);
    }
    
    @Override
    public InventoryItem getItemById(Long id) {
        return inventoryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
    }

    @Override
    public List<InventoryItem> getAllItems() {
        return inventoryItemRepository.findByIsDeletedFalse(); // Only fetch non-deleted items
    }
}
