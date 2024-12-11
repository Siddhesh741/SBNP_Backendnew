package com.photoshoot.org.Service;

import com.photoshoot.org.Entity.InventoryItem;  

import java.util.List;  

public interface InventoryItemService {  
    InventoryItem saveItem(InventoryItem item);  
    InventoryItem updateItem(Long id, InventoryItem item);  
    void deleteItem(Long id);  
    void softDeleteItem(Long id); // Soft delete
    InventoryItem getItemById(Long id);  
    List<InventoryItem> getAllItems();  
}
