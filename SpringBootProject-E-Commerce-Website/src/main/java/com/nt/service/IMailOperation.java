package com.nt.service;

public interface IMailOperation {
	//Send verification code (OTP) on give email
	public boolean sendVerficationCode(String email);
	//Send email for successful registration
	public String sendSuccessfulRegistrationMail(String userName, String email);
}
