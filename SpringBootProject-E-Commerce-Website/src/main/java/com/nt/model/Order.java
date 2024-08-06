package com.nt.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="orders")
public class Order {
	
	@Column(name="order_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private Integer userId;
	private String status;
	@Column(name = "orderDate", nullable = false, updatable = false)
	private Timestamp orderDate=new Timestamp(System.currentTimeMillis());
	private String paymentType;
	private int quantity;
	private int price;
	
	@ManyToOne(targetEntity=Products.class,fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	//@JoinColumn(name="productRefNo", referencedColumnName="product_id")
	@JoinColumn(name="productId", referencedColumnName="product_id")
	private Products productDetails;

}//End of Order class
