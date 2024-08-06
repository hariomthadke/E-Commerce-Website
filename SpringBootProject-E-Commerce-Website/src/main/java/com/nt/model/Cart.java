package com.nt.model;

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
@Table(name="cart")
public class Cart {
	
	@Column(name="cart_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
   private Integer id;
   private Integer userId;
   private Integer quantity;
   
	@ManyToOne(targetEntity=Products.class,fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	//@JoinColumn(name="productRefNo", referencedColumnName="product_id")
	@JoinColumn(name="productId", referencedColumnName="product_id")
	private Products productDetails;
	
}//End of Cart class
