package com.springboot1.service;


import java.util.List;

import com.springboot1.model.activity;
import com.springboot1.model.delivery;
import com.springboot1.model.users;


public interface IDeliveryService {
    
    void saveDelivery(delivery delivery);
    delivery getDeliveryById(int idDelivery);
    void createDeliveries(List<delivery> deliveries);
    delivery getDeliveriesByUserAndActivity(users user, activity activity);
    List<delivery> getDeliveriesByActivity(activity activity);
    List<delivery> getDeliveriesByPending(int pending, users user);
    void deleteDeliveriesOfActivity(List<delivery> deliveries);

}
