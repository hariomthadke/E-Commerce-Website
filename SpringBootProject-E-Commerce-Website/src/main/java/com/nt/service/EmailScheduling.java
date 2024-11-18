package com.nt.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nt.model.NewStock;
import com.nt.model.Notify;
import com.nt.model.Products;
import com.nt.repository.INewStockRepo;
import com.nt.repository.INotificationRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component("notification")
public class EmailScheduling {

	@Autowired
	private JavaMailSender sender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private INotificationRepo repo;

	@Autowired
	private IProductsService productService;

	@Autowired
	private IUsersService userService;
	
	@Autowired
	private INewStockRepo newStockRepo;

	//@Scheduled(cron = "0 0 9,18 * * ?")
	//@Scheduled(cron="0 * * * * *")
	public void sendNotification() throws IOException {

		ArrayList<Notify> list=(ArrayList<Notify>) notificationService.getAllNotificationDetails();
		HashMap<Integer, ArrayList<String>> multiValueMap = new HashMap<>();

		for(int i=0;i<list.size();i++) {
			Notify record=list.get(i);
			Integer productId=record.getProductId();
			if (!multiValueMap.containsKey(productId)) {
				multiValueMap.put(productId, new ArrayList<>());
			}
			multiValueMap.get(productId).add(userService.getUserById(record.getUserId()).getEmail());
		}
		
		List<NewStock> newStockList=(List<NewStock>) newStockRepo.findAll();
		HashMap<Integer, Integer> newStockRecord=new HashMap<Integer, Integer>();
		
		for(int i=0;i<newStockList.size();i++) {
			newStockRecord.put(newStockList.get(i).getPid(), productService.findProductById(newStockList.get(i).getPid()).getQuantity());		
		}
		
		
		//Mail sending logic
		try {
			for(Integer product:multiValueMap.keySet()) {
				Products currentProduct=productService.findProductById(product);		
				System.out.println(currentProduct.getImage());
				String subject=currentProduct.getName() + "is now Available in Stock";
				String msg="<!DOCTYPE html>\r\n"
						+ "<html lang=\"en\">\r\n"
						+ "<head>\r\n"
						+ "    <meta charset=\"UTF-8\">\r\n"
						+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
						+ "    <title>New Stock Alert</title>\r\n"
						+ "    <style>\r\n"
						+ "        body {\r\n"
						+ "            font-family: Arial, sans-serif;\r\n"
						+ "            margin: 0;\r\n"
						+ "            padding: 0;\r\n"
						+ "            background-color: #f4f4f4;\r\n"
						+ "        }\r\n"
						+ "        .container {\r\n"
						+ "            width: 80%;\r\n"
						+ "            margin: 20px auto;\r\n"
						+ "            background-color: #fff;\r\n"
						+ "            padding: 20px;\r\n"
						+ "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\r\n"
						+ "        }\r\n"
						+ "        .header {\r\n"
						+ "            text-align: center;\r\n"
						+ "            background-color: #0288D1;\r\n"
						+ "            color: white;\r\n"
						+ "            padding: 20px;\r\n"
						+ "            border-radius: 8px 8px 0 0;\r\n"
						+ "        }\r\n"
						+ "        .header h1 {\r\n"
						+ "            margin: 0;\r\n"
						+ "        }\r\n"
						+ "        .content {\r\n"
						+ "            padding: 20px;\r\n"
						+ "        }\r\n"
						+ "        .content p {\r\n"
						+ "            font-size: 16px;\r\n"
						+ "            line-height: 1.6;\r\n"
						+ "            color: #333;\r\n"
						+ "        }\r\n"
						+ "        .content img {\r\n"
						+ "            max-width: 100%;\r\n"
						+ "            height: auto;\r\n"
						+ "            border-radius: 8px;\r\n"
						+ "            margin: 20px 0;\r\n"
						+ "        }\r\n"
						+ "        .button {\r\n"
						+ "            text-align: center;\r\n"
						+ "            margin-top: 20px;\r\n"
						+ "        }\r\n"
						+ "        .button a {\r\n"
						+ "            background-color: #0288D1;\r\n"
						+ "            color: white;\r\n"
						+ "            padding: 10px 20px;\r\n"
						+ "            text-decoration: none;\r\n"
						+ "            border-radius: 5px;\r\n"
						+ "            font-size: 18px;\r\n"
						+ "        }\r\n"
						+ "        .button a:hover {\r\n"
						+ "            background-color: #0277BD;\r\n"
						+ "        }\r\n"
						+ "        .footer {\r\n"
						+ "            text-align: center;\r\n"
						+ "            font-size: 12px;\r\n"
						+ "            color: #888;\r\n"
						+ "            margin-top: 30px;\r\n"
						+ "        }\r\n"
						+ "    </style>\r\n"
						+ "</head>\r\n"
						+ "<body>\r\n"
						+ "\r\n"
						+ "    <div class=\"container\">\r\n"
						+ "        <div class=\"header\">\r\n"
						+ "            <h1>Exciting News! New Stock is Now Available</h1>\r\n"
						+ "        </div>\r\n"
						+ "\r\n"
						+ "        <div class=\"content\">\r\n"
						+ "            <p>We are thrilled to inform you that our latest products are now back in stock! If you've been waiting for a restock of your favorite items, now is the perfect time to act.</p>\r\n"
						+ "            \r\n"
						+ "            <p>Explore our updated inventory, filled with high-quality items that cater to your needs. From the latest tech gadgets to stylish apparel, we have something for everyone. Don’t miss out on this opportunity to get your hands on the best products!</p>\r\n"
						+ "\r\n"
						+ "            <img src='cid:image' alt=\"New stock image\">\r\n"
						+ "\r\n"
						+ "            <p>Simply click the button below to start shopping and take advantage of the new arrivals before they sell out again. We're excited to have you back, and we can't wait to serve you with the latest and greatest products.</p>\r\n"
						+ "        </div>\r\n"
						+ "\r\n"
						+ "        <div class=\"button\">\r\n"
						+ "            <a href=\"https://yourwebsite.com/shop\" target=\"_blank\">Shop Now</a>\r\n"
						+ "        </div>\r\n"
						+ "\r\n"
						+ "        <div class=\"footer\">\r\n"
						+ "            <p>&copy; 2024 Your Company Name. All rights reserved. | <a href=\"https://yourwebsite.com/unsubscribe\" target=\"_blank\">Unsubscribe</a></p>\r\n"
						+ "        </div>\r\n"
						+ "    </div>\r\n"
						+ "\r\n"
						+ "</body>\r\n"
						+ "</html>\r\n"
						+ "";

				String[] array=new String[multiValueMap.get(product).size()];
				
				
				// Create a MimeMessage object
				MimeMessage message = sender.createMimeMessage();
				// Create a helper object to set the properties of the MimeMessage
				MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
				// Set the sender's email address
				helper.setFrom(fromEmail);
				// Set the recipient email addresses
				helper.setTo("hariomthadke@gmail.com");
				
				helper.setBcc(multiValueMap.get(product).toArray(array));

				// Set the subject of the email
				helper.setSubject(subject);	        
				// Set the date the email is sent
				helper.setSentDate(new Date());        
				// Set the content of the email (true indicates the content is HTML)
				helper.setText(msg, true);    

				
				// Get the image name from currentProduct and use a relative path
				String imageName = currentProduct.getImage();
				ClassPathResource imageResource = new ClassPathResource("static/uploads/" + imageName);
				// Attach the image
		        FileSystemResource res = new FileSystemResource(imageResource.getFile());
				helper.addInline("image", res);
				sender.send(message);
			}



		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	
	 int count=0;
	
	@Async("taskExecutor")
	public void mytrial() throws MessagingException, InterruptedException {
		
		// Create a MimeMessage object
		MimeMessage message = sender.createMimeMessage();
		// Create a helper object to set the properties of the MimeMessage
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		// Set the sender's email address
		helper.setFrom(fromEmail);
		// Set the recipient email addresses
		helper.setTo("hariomthadke@gmail.com");
		
		helper.setBcc("2021bcs050@sggs.ac.in");

		// Set the subject of the email
		helper.setSubject("count: "+count);	        
		// Set the date the email is sent
		helper.setSentDate(new Date());        
		// Set the content of the email (true indicates the content is HTML)
		helper.setText("count: "+ count, true);  
		
        
        	System.out.println("start:: "+count);
		sender.send(message);
		System.out.println("end:: "+count);
		System.out.println("Thread: " + Thread.currentThread().getName() + " - start:: " + count);
		count++;
//		Thread.sleep(1000);
//		System.out.println(count++);
        }
	
	
	
	
	

	
}
