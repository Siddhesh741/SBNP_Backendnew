package com.photoshoot.org.Entity;  
import jakarta.persistence.*;  
import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@Entity  
@Getter  
@Setter  
@NoArgsConstructor  
@AllArgsConstructor  
@Table(name = "InventoryItem")  
public class InventoryItem {  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  

	@Column(nullable = true)  
    private String createdBy;  

	@Column(nullable = false)  
    private String updatedBy;  
    
    @Column(name = "item_Name")  
    private String itemName;  
    
    @Column(name = "date")  
    private String date;  
    
    @Column(name = "quantity")  
    private Integer quantity;  
    
    @Column(name = "comment")  
    private String comment;  
    
 // New field for soft delete flag
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    /**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	
	
	
	
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the quantity
	 */
	public String getcomment() {
		return comment;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setcomment(String comment) {
		this.comment = comment;
	}

	 // Getter and setter methods for the isDeleted field
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
	 
	
  
}  