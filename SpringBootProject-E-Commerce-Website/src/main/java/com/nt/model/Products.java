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
@Table(name="products")
public class Products {
	
	@Column(name="product_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	@Column(name="description", length = 1000)
	private String description;
	private Integer price;
	private Integer discount;
	private Integer quantity;
	private Integer categoryType;
	@Column(name="imagePath")
	private String image;
	private Integer priceAfterDiscount;
	
}//End of Products class
