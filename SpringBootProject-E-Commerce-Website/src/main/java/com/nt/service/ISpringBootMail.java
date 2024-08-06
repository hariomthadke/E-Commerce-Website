package com.nt.service;

public interface ISpringBootMail {
	//Send mail
    public boolean sendMail(String subject, String msg, String[] emails) throws Exception;
    
}
