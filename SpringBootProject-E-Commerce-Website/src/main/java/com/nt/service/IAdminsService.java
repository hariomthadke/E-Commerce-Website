package com.nt.service;

import java.util.List;
import java.util.Optional;

import com.nt.model.Admins;
import com.nt.model.Users;

public interface IAdminsService {
	  //Register admin account
	  public String registerAdmin(Admins admin, String password);
	  //Get all admin
      public List<Admins> getAllAdmins();
      //Delete admin by admin id
      public String deleteAdminById(Integer id);
      //Find admin by email (username)
      public Optional<Admins> findByUsername(String username);
}
