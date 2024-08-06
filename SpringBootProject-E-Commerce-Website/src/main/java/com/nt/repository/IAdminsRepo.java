package com.nt.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.nt.model.Admins;

public interface IAdminsRepo  extends CrudRepository<Admins,Integer>{
	//Find Admin by email
	public Optional<Admins> findByEmail(String email);
}
