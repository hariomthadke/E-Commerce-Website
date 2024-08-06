package com.nt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nt.model.Users;

import jakarta.transaction.Transactional;

public interface IUsersRepo extends CrudRepository<Users, Integer> {
	//Find user by email
     public Optional<Users> findByEmail(String username);

     //Deactive user
     @Modifying
     @Transactional
     @Query("UPDATE Users u SET u.activeStatus = 0 WHERE u.email = :email")
      public void updateEnabledStatus(@Param("email") String email);
     
     //Get all active users
     List<Users> findByActiveStatus(Integer activeStatus);
}
