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
@Table(name="newStock")
public class NewStock {
	@Column(name="newStock_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private Integer pid;
}
