package com.nt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.model.Message;
import com.nt.model.Account;
import com.nt.model.Users;
import com.nt.repository.IUsersRepo;
import com.nt.repository.IAccountRepository;

import jakarta.servlet.http.HttpSession;

@Service("usersService")
public class UsersServiceImpl implements IUsersService {

	@Autowired
	HttpSession session;
	@Autowired
	private IUsersRepo repo;

	@Autowired
	private IAccountRepository accountRepo;


	////Register user account
	@Override
	public String registerUser(Users user, String password) {
		//Store user details to database
		Users us=repo.save(user);

		//Create Account object and set userCredentials
		Account userCredentials=new Account();
		userCredentials.setUsername(user.getEmail());
		userCredentials.setRole("user");
		userCredentials.setPassword(password);

		//Store user credentials to database
		accountRepo.save(userCredentials);

		//return message
		return "Your are successfully register with us, please login to continue";
	}

	
	//Update user details
	@Override
	public String updateUser(Users user) {
		repo.save(user);
		return "User updated ";
	}

	
	//Get all users
	@Override
	public List<Users> getAllUsers() {
		return (List<Users>) repo.findByActiveStatus(1);
	}

	
	//Delete user account by user id
	// The actual user details will not be deleted; instead, their account will be deactivated
	@Override
	public String deleteUserAccountByUserId(Integer userId) {
		//Get user by user id
		Optional<Users> user=repo.findById(userId);
		
		if(user.isPresent()) {//If user present
			//Get user credentials from Account 
			Account account=accountRepo.getAccountByUsername(user.get().getEmail());
			if(account!=null) {
				//Update enabled status to 0 in Users table
				repo.updateEnabledStatus(account.getUsername());
				//Update enabled status to 0 in Account table
				accountRepo.updateEnabledStatus(account.getUsername());
				//Display message
				Message message = new Message("User Deleted Successfully", "success", "alert-success");
				session.setAttribute("message", message);
			}
		}
		
	   //return message
		return "User record is deleted Successfully";
	}

	
	//Get user by user id
	@Override
	public Users getUserById(Integer userId) {
		//Get user object by user id
		Optional<Users> user=repo.findById(userId);
		if(user.isPresent()) {//If user available
			return user.get();
		}
		return null;
	}

	
	//Check  given email id is available or not in Account table
	@Override
	public boolean isEmailAvailable(String email) {
		//Get account details by email
		Account account=accountRepo.getAccountByUsername(email);

		if(account!=null) {//If account objec not null
			//Check account enabled status 
			if(account.isEnabled()) return true; //If account is enabled return true
		}
		
		return false; //Email is not found or Account is deactivated
	}

	
	//Change user account password
	//Work for both user & Admin
	@Override
	public String changePassword(String email, String password) {
		if(this.isEmailAvailable(email)) {//If Email is Available
			//Update account password
			accountRepo.updatePassword(email, password);
		}
		
		//return message
		return "Password updated successfully!";
	}

	
	//Find user by its email (username)
	@Override
	public Optional<Users> findByUsername(String username) {
		return repo.findByEmail(username);
	}

	
	//Check user is active or not
	@Override
	public boolean isUserActiveOrNot(Integer id) {
		//Get User by user id
		Optional<Users> user=repo.findById(id);
		if(user.isPresent()) {//If user available
			//Check user active status
			if(user.get().isUserActive()) return true;
		}
		return false;
	}



}
