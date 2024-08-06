package com.nt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="admins")
public class Admins {

	@Column(name="admin_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	@Column(name="email", length=30, unique=true)
	private String email;
	@Column(name="mobileNo", length=13)
	private String mobileNo;
	
}//End of Admins class
