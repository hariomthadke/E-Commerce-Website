package com.nt.service;

import java.util.List;
import java.util.Optional;

import com.nt.model.Users;

public interface IUsersService {
	//Register user account
	public String registerUser(Users user, String password);
	//Update user details
	public String updateUser(Users user);
	//Get all users
	public List<Users> getAllUsers();
	//Delete user account by user id
	public String deleteUserAccountByUserId(Integer id);
	//Get user by user id
	public Users getUserById(Integer id);
	//Check  given email id is available or not in Account Table
	public boolean isEmailAvailable(String email);
	//Change user account password
	public String changePassword(String email, String password);
	//Find user by its email (username)
	public Optional<Users> findByUsername(String username);
	//Check user active or not
	public boolean isUserActiveOrNot(Integer id);
	//Register user which user option login as google or facebook
	public boolean registerOauthUser(String email, String name);
	public boolean isOauthEmailAvailable(String email);
}
