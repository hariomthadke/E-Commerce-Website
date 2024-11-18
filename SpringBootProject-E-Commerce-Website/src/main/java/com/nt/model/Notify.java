package com.nt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="notify")
public class Notify {
	@Column(name="notification_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private Integer userId;
	private Integer productId;
}




