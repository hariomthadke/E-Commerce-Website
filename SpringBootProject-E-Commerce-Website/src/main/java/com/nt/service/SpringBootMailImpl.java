package com.nt.service;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.io.UTF32Reader;

import jakarta.mail.internet.MimeMessage;

@Service("gmailservice")
public class SpringBootMailImpl implements ISpringBootMail  {
	
	@Autowired
	private JavaMailSender sender;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	public boolean sendMail(String subject, String msg, String[] emails) {
	    try {
	        // Create a MimeMessage object
	        MimeMessage message = sender.createMimeMessage();
	        // Create a helper object to set the properties of the MimeMessage
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        
	        // Set the sender's email address
	        helper.setFrom(fromEmail);        
	        // Set the recipient email addresses
	        helper.setTo(emails);	        
	        // Set the subject of the email
	        helper.setSubject(subject);	        
	        // Set the date the email is sent
	        helper.setSentDate(new Date());        
	        // Set the content of the email (true indicates the content is HTML)
	        helper.setText(msg, true);        
	        
	        
	        // Send the email
	        sender.send(message);
	    } catch(Exception e) {
	        // Print the stack trace if an exception occurs and return false
	        e.printStackTrace();
	        return false;
	    }
	    
	    // Return true if the email is sent successfully
	    return true;
	}


	
}
