package com.nt.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.model.Notify;
import com.nt.repository.INotificationRepo;

@Service
public class NotificationServiceImpl implements INotificationService {

	@Autowired
	private INotificationRepo repo;
	@Override
	public boolean addProductNotification(Integer userId, Integer productId) {
		Notify notification=new Notify();
		notification.setProductId(productId);
		notification.setUserId(userId);
	    try {
		repo.save(notification);
	    }catch(Exception e) {return false;}
		return true;
	}

	@Override
	public boolean isNotificationAlreadyAvailable(Integer userId, Integer productId) {
		List<Notify> list=repo.findNotificationsByProductAndUser(productId, userId);
		if(list.size()==1) return true;
		return false;
	}

	@Override
	public List<Notify> getAllNotificationDetails() {
		ArrayList<Notify> list=(ArrayList<Notify>) repo.findAll();
		Collections.sort(list, (a, b)-> a.getProductId() - b.getProductId());
		  return list;
	}

}
