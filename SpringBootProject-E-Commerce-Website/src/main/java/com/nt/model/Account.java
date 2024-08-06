package com.nt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String role;
    private Integer enabled;

    //Assign defalut value to 'enabled'
    @PrePersist
    public void prePersist() {
        if (this.enabled == null) {
            this.enabled=1; // Default value
        }
	}
   
    //Check user is active or not
	public boolean isEnabled() {
		if(enabled==1) return true; //active
		else return false; //deactive
		
	}
}//End of User class

