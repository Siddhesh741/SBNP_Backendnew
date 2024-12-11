package com.photoshoot.org.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.photoshoot.org.Entity.InventoryItem;  

@Repository  
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

	List<InventoryItem> findAll();  
	  List<InventoryItem> findByIsDeletedFalse();
} 
