package com.nt.service;

import java.util.List;

import com.nt.model.Notify;

public interface INotificationService {
     public boolean addProductNotification(Integer userId, Integer productId);
     public boolean isNotificationAlreadyAvailable(Integer userId, Integer productId);
     public List<Notify> getAllNotificationDetails();
}
