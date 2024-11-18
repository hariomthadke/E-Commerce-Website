package com.nt.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class PrintService {
int i=0;
    @Async("taskExecutor")
    public void printHello() {
        System.out.println("Hello " + i);
        i++;
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(10000); // Sleep for 10 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
        
        int count=1;
        @Async("taskExecutor")
    	public void mytrial() throws MessagingException, InterruptedException {
    		
//    		// Create a MimeMessage object
//    		MimeMessage message = sender.createMimeMessage();
//    		// Create a helper object to set the properties of the MimeMessage
//    		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//    		// Set the sender's email address
//    		helper.setFrom(fromEmail);
//    		// Set the recipient email addresses
//    		helper.setTo("hariomthadke@gmail.com");
//    		
//    		helper.setBcc("2021bcs050@sggs.ac.in");
    //
//    		// Set the subject of the email
//    		helper.setSubject("count: "+count);	        
//    		// Set the date the email is sent
//    		helper.setSentDate(new Date());        
//    		// Set the content of the email (true indicates the content is HTML)
//    		helper.setText("count: "+ count, true);  
//    		
//            
//            	System.out.println("start:: "+count);
//    		sender.send(message);
//    		System.out.println("end:: "+count);
    		System.out.println("Thread: " + Thread.currentThread().getName() + " - start:: " + count);
    		Thread.sleep(1000);
    		System.out.println(count++);
            }
    }


