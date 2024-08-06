package com.nt.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="userDetails")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Users {
      
	@Column(name="user_id", nullable = false, updatable = false)
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name="name", length=30)
	private String name;
	@Column(name="email", length=30, unique=true)
	private String email;
	@Column(name="mobileNo", length=13)
	private String mobileNo;
	@Column(name="gender", length=6)
	private String gender;
	@Column(name="address", length=50)
	private String address;
	@Column(name="city", length=15)
	private String city;
	@Column(name="pincode", length=10)
	private String pincode;
	@Column(name="state", length=20)
	private String state;
	@Column(nullable=false, updatable=false)
	private Integer activeStatus;	
	@Column(name = "registerDate", nullable = false, updatable = false)
    private LocalDate registrationDate=LocalDate.now();
	
	//Assign default value to 'activeStatus'
	@PrePersist
    public void prePersist() {
        if (this.activeStatus == null) {
            this.activeStatus=1; // Default value
        }
	}
	
	//Check user active status
	public boolean isUserActive() {
		if(activeStatus==1) return true; //active
		else return false;  //deactivae
	}
	
}//End of Users class
