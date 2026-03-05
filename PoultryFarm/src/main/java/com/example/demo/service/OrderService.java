package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.OrderRequest;
import com.example.demo.repository.OrderRepository;


@Service
public class OrderService {

    @Autowired
    private OrderRepository repo;

    public void placeOrder(OrderRequest order) {
        order.setStatus("PENDING");
        repo.save(order);
    }

    public List<OrderRequest> getBuyerOrders(String username) {
        return repo.findByBuyerUsername(username);
    }

    public List<OrderRequest> getFarmerOrders(String username) {
        return repo.findByProductFarmerUsername(username);
    }

    public void acceptOrder(Long id) {
        OrderRequest order = repo.findById(id).get();
        order.setStatus("ACCEPTED");
        repo.save(order);
    }

    public void rejectOrder(Long id) {
        OrderRequest order = repo.findById(id).get();
        order.setStatus("REJECTED");
        repo.save(order);
    }
}