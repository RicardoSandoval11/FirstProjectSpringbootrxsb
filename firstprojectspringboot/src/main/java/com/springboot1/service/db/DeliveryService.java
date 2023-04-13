package com.springboot1.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot1.model.activity;
import com.springboot1.model.delivery;
import com.springboot1.model.users;
import com.springboot1.repository.DeliveryRepository;
import com.springboot1.service.IDeliveryService;

@Service
public class DeliveryService implements IDeliveryService{

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public void createDeliveries(List<delivery> deliveries){
        deliveryRepository.saveAll(deliveries);
    }

    @Override
    public void saveDelivery(delivery delivery) {
        deliveryRepository.save(delivery);
    }

    @Override
    public delivery getDeliveryById(int deliveryId){
        Optional<delivery> optional = deliveryRepository.findById(deliveryId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public delivery getDeliveriesByUserAndActivity(users user, activity activity){
        return deliveryRepository.findByUserAndActivity(user, activity);
    }

    @Override
    public List<delivery> getDeliveriesByActivity(activity activity) {
        return deliveryRepository.findByActivity(activity);
    }

    @Override
    public List<delivery> getDeliveriesByPending(int pending, users user){
        return deliveryRepository.findByPendingAndUser(pending, user);
    }

    @Override
    public void deleteDeliveriesOfActivity(List<delivery> deliveries){
        deliveryRepository.deleteAll(deliveries);
    }
    
}
