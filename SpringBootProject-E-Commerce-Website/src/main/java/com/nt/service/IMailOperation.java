package com.nt.service;

public interface IMailOperation {
	//Send verification code (OTP) on give email
	public void sendVerficationCode(String email, Integer otp);
	//Send email for successful registration
	public void sendSuccessfulRegistrationMail(String userName, String email);
}
