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
public class Calculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookingId;
    @Column(nullable = false) // Ensure it's not null
    private String date; // Keep as String since the database stores it as VARCHAR(255)

    // Change from primitive 'int' to 'Integer'
    private Integer actualAmount;
    private String whoReceivedMoney;
    
    // Change from primitive 'int' to 'Integer'
    private Integer roomMaintenance;
    private Integer sbnpBalance;
    private Integer taranginiiBalance;
    private Integer totalIncome;
    private String settlementMessage;

    @Column(length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'Y'")
    private String submitted = "Y"; // Default value is "Y"
    private String submittedBy; 
    private String editedBy;  
    // Getters and Setters for each field remain the same
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Integer actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getWhoReceivedMoney() {
        return whoReceivedMoney;
    }

    public void setWhoReceivedMoney(String whoReceivedMoney) {
        this.whoReceivedMoney = whoReceivedMoney;
    }

    public Integer getRoomMaintenance() {
        return roomMaintenance;
    }

    public void setRoomMaintenance(Integer roomMaintenance) {
        this.roomMaintenance = roomMaintenance;
    }

    public Integer getSbnpBalance() {
        return sbnpBalance;
    }

    public void setSbnpBalance(Integer sbnpBalance) {
        this.sbnpBalance = sbnpBalance;
    }

    public Integer getTaranginiiBalance() {
        return taranginiiBalance;
    }

    public void setTaranginiiBalance(Integer taranginiiBalance) {
        this.taranginiiBalance = taranginiiBalance;
    }

    public Integer getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Integer totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getSettlementMessage() {
        return settlementMessage;
    }

    public void setSettlementMessage(String settlementMessage) {
        this.settlementMessage = settlementMessage;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }
    
    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }
    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

	public static void save(Calculation savedCalculation) {
		// TODO Auto-generated method stub
		
	}

	


}
