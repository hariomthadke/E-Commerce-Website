package com.nt.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class MailOperationImpl implements IMailOperation {

	@Autowired
	private HttpSession session;
	@Autowired
	private ISpringBootMail mail;

	//Send verification code (OTP) on give email
	@Override
	public boolean sendVerficationCode(String email) {
		boolean flag;
		//Create Random object and generate random verification code (OTP)
		Random random = new Random();
		Integer verificationCode = random.nextInt(12345, 99999);

		try {
			// Define the email subject
			String subject = "Eazy Deals Verification Code";
			// Construct the email body with HTML content
			String body = "<html><body>"
					+ "<h2>Your Verification Code</h2>"
					+ "<p>Hi,</p>"
					+ "<p>Your verification code is:</p>"
					+ "<h3 style='color: blue;'>" + verificationCode + "</h3>"
					+ "<p>If you did not request this code, please report it to <a href='mailto:hariomthadke@gmail.com'>hariomthadke@gmail.com</a>.</p>"
					+ "<p>Best regards,</p>"
					+ "<p>The Eazy Deals Team</p>"
					+ "</body></html>";
			// Create an array with the recipient's email address
			String[] recipients = new String[]{email};
			
			// Send the email
			flag = mail.sendMail(subject, body, recipients);
			
			// Store the verification code and email in the session
			session.setAttribute("verificationCode", verificationCode);
			session.setAttribute("email", email);
		} catch (Exception e) {
			// Print the stack trace if an exception occurs
			e.printStackTrace();
			// Set flag to false if an exception occurs
			flag=false;
		}

		// Return the flag indicating success or failure
		return flag;
	}


	//Send email for successful registration
	@Override
	public String sendSuccessfulRegistrationMail(String userName, String email) {
		// Define the email subject
		String subject = " Welcome to EazyDeals, " + userName+ "!";
		// Construct the email body with HTML content
		String body = "<h2 style='color: #1a73e8;'>Hello " + userName + ",</h2>"
				+ "<p style='font-size: 1.1em;'>Congratulations! 🎉</p>"
				+ "<p> Warm welcome to <strong>EazyDeals</strong>! We are thrilled to have you as a part of our growing community. Thank you for choosing us for your online shopping needs.</p>"
				+ "<p>Your registration was a success, and we can't wait for you to dive into the amazing world of EazyDeals. Here’s what awaits you:</p>"
				+ "<ul style='list-style-type: none; padding: 0;'>"
				+ "  <li>💥 <strong>Exclusive Deals:</strong> Grab the best discounts and offers tailored just for you.</li>"
				+ "  <li>🛒 <strong>Wide Product Range:</strong> Explore a variety of products that cater to all your needs.</li>"
				+ "  <li>🌟 <strong>Seamless Shopping:</strong> Enjoy a smooth and hassle-free shopping experience.</li>"
				+ "</ul>"
				+ "<p>We’re here to make your shopping experience delightful and rewarding. If you have any questions, feel free to reach out. We’re always here to help!</p>"
				+ "<p>Happy shopping, and welcome once again! 🎊</p>"
				+ "<p style='font-size: 1.1em; color: #1a73e8;'>Warm regards,<br>"
				+ "The EazyDeals Team</p>";

		try {
			//Send the email
			mail.sendMail(subject, body, new String[]{email});
		} catch (Exception e) {
			// Print the stack trace if an exception occurs
			e.printStackTrace();
			// Return message
			return "Unable to sent Registration mail";
		}
        //Return message
		return "Registration successful Mail sent successfully";
	}



}
