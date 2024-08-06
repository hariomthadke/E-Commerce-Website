package com.nt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.model.Admins;
import com.nt.model.Account;
import com.nt.repository.IAdminsRepo;
import com.nt.repository.IAccountRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminsServiceImpl implements IAdminsService {

	@Autowired
	HttpSession session;

	@Autowired
	private IAccountRepository accountRepo;

	@Autowired
	private IAdminsRepo repo;
	//Register admin account
	@Override
	public String registerAdmin(Admins admin, String password) {
		//save admin details in database
		Admins ad=repo.save(admin);
		
		//create Account obj and save admin credentials into Account object
		Account adminCredentials=new Account();
		adminCredentials.setUsername(ad.getEmail());
		adminCredentials.setRole("admin");
		adminCredentials.setPassword(password);
		//Store adminCredentials to database
		accountRepo.save(adminCredentials);
		
		//return message
		return "Admin obj is saved with id value:"+ad.getId();

	}

	 //Get all admin
	@Override
	public List<Admins> getAllAdmins() {
		//return all admin list
		return (List<Admins>) repo.findAll();
	}

	 //Delete admin by admin id
	@Override
	public String deleteAdminById(Integer id) {
		//Get admin details by admin id
		Optional<Admins> admin=repo.findById(id);
		String username="";
		//Get username (email)
		if(admin.isPresent()) {
			username=admin.get().getEmail();
		}
		
		//Delete admin by admin id from Admins table
		repo.deleteById(id);
		//Delete admin creadentials from Account table
		if(!username.isBlank())
		accountRepo.deleteAccountByUsername(username);
		//return message
		return "Admin record is deleted Successfully";
	}

	//Find admin by email (username)
	@Override
	public Optional<Admins> findByUsername(String username) {
		return repo.findByEmail(username);
	}

}
