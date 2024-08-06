package com.nt.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nt.model.Account;

import jakarta.transaction.Transactional;

public interface IAccountRepository extends CrudRepository<Account, Integer> {

	//Get account details by username (email)
    @Query("SELECT u FROM Account u WHERE u.username = :username")
    public Account getAccountByUsername(@Param("username") String username);
    
    //Update account password
    @Modifying
    @Transactional
    @Query("UPDATE Account u SET u.password = :password WHERE u.username = :email")
     public void updatePassword(@Param("email") String email, @Param("password") String password);
    
    //Deactive account
    @Modifying
    @Transactional
    @Query("UPDATE Account u SET u.enabled = 0 WHERE u.username = :username")
     public void updateEnabledStatus(@Param("username") String username);
    
    //Delete account by username
    @Query("DELETE FROM Account u WHERE  u.username = :username")
    public void deleteAccountByUsername(@Param("username") String username);
    
}
