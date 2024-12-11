package com.photoshoot.org.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String clientName;
	
	@Column(nullable = false)
	private Long clientPhoneNo;
	
	@Column(nullable = false)
	private String clientEmail;
	
	@Column(nullable = false)
	private String photographerName;
	
	@Column(nullable = false)
	private Long photographerPhoneNo;

	@Column(nullable = false)
	private String photographerEmail;
	
	@Column(nullable = false)
	private String selectDate;
	
	@Column(nullable = false)
	private String selectedPackage;
	
	@Column(nullable = false)
	private Integer packageAmount;
	
	@Column(nullable = false)
	private Integer actualAmount;
	
	@Column(nullable = false)
	private String  paymentMode;

	@Column(nullable = false)
	private String cashcollectedby;
	
	@Column(nullable = false)
	private String cityName;
	
	@Column(nullable = false)
	private String knowaboutlocation;
	
	@Column(nullable = false)
	private Integer visitorsCount;
	
	@Column(nullable = false)
	private String  agreeTerms;
	
	@Column(nullable = false)
	private String createdBy; //New column to track who created the client
	
	@Column(name = "edited_by") // New column to track who edited the client
	private String editedBy;

	@Column(nullable = false, unique = true) 
	private String bookingId; // New field for unique ID
	  
	@Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false; // Default to false (not deleted)
	
	
	

	
	
	
	//Getter and Setter Methods
	public Long getId() {
		return id;
	}
    public void setId(Long id) {
		this.id = id;
	}
    

    public String getclientName() {
		return clientName;
	}
	public void setclientName(String clientName) {
		this.clientName = clientName;
	}
	
	
	public Long getclientPhoneNo() {
		return clientPhoneNo;
	}
	public void setclientPhoneNo(Long clientPhoneNo) {
		this.clientPhoneNo = clientPhoneNo;
	}
	
	
	public String getclientEmail() {
		return clientEmail;
	}
	public void setclientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	
	
	public String getphotographerName() {
		return photographerName;
	}
	public void setphotographerName(String photographerName) {
		this.photographerName = photographerName;
	}
	
    
	public Long getphotographerPhoneNo() {
		return photographerPhoneNo;
	}
    public void setphotographerPhoneNo(Long photographerPhoneNo) {
		this.photographerPhoneNo = photographerPhoneNo;
	}
	
	
	public String getphotographerEmail() {
		return photographerEmail;
	}
	public void setphotographerEmail(String photographerEmail) {
		this.photographerEmail = photographerEmail;
	}
	
    public String getselectDate() {
		return selectDate;
	}
	public void setselectDate(String selectDate) {
		this.selectDate = selectDate;
	}
	
	
	public String getselectedPackage() {
		return selectedPackage;
	}
	public void setselectedPackage(String selectedPackage) {
		this.selectedPackage = selectedPackage;
	}
	
	
	public Integer getpackageAmount() {
		return packageAmount;
	}
	public void setpackageAmount(Integer packageAmount) {
		this.packageAmount = packageAmount;
	}
	
	
	public Integer getactualAmount() {
		return actualAmount;
	}
	public void setactualAmount(Integer actualAmount) {
		this.actualAmount= actualAmount;
	}

	
	public String getcashcollectedby() {
		return cashcollectedby;
	}
	public void setcashcollectedby(String cashcollectedby) {
		this.cashcollectedby = cashcollectedby;
	}
	
	
	public String getpaymentMode() {
		return paymentMode;
	}
	public void setpaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	public Integer getvisitorsCount() {
		return visitorsCount;
	}
	public void setvisitorsCount(Integer visitorsCount) {
		this.visitorsCount= visitorsCount;
	}
	
	
	public String getcityName() {
		return cityName;
	}
    public void setcityName(String cityName) {
		this.cityName = cityName;
	}
    
	public String getknowaboutlocation() {
		return knowaboutlocation;
	}
	public void setknowaboutlocation(String knowaboutlocation) {
		this.knowaboutlocation = knowaboutlocation;
	}
	
	public String getagreeTerms() {
		return agreeTerms;
	}
	public void setagreeTerms(String  agreeTerms) {
		this.agreeTerms =  agreeTerms;
	}
	
	
	public String getCreatedBy() {
	    return createdBy;
	}

	public void setCreatedBy(String createdBy) {
	    this.createdBy = createdBy;
	}
	
	
	   
    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }
    
	 
    
    public String getBookingId() {
        return bookingId;
    }
	  
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    
    
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
	    
}
	