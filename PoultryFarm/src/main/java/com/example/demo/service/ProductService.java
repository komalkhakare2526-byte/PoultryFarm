package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public void save(Product product) {
        repo.save(product);
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public List<Product> getFarmerProducts(String username) {
        return repo.findByFarmerUsername(username);
    }

    public Product getById(Long id) {
        return repo.findById(id).orElse(null);
    }
    
    public void delete(Long id) {
        repo.deleteById(id);
    }
}