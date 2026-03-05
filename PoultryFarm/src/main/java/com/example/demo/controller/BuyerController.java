package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.OrderRequest;
import com.example.demo.entity.Product;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "buyer-dashboard";
    }

    @GetMapping("/marketplace")
    public String marketplace(Model model) {
        model.addAttribute("products", productService.getAll());
        return "marketplace";
    }

    @PostMapping("/request-order")
    public String order(Long productId,
                        Integer quantity,
                        Authentication auth) {

        Product product = productService.getById(productId);

        // 🔥 Fetch existing logged-in buyer from DB
        Optional<AppUser> buyer = userRepository.findByUsername(auth.getName());

        OrderRequest order = new OrderRequest();
        order.setProduct(product);
        order.setBuyer(buyer.get());
        order.setQuantity(quantity);

        orderService.placeOrder(order);

        return "redirect:/buyer/orders";
    }

    @GetMapping("/my-orders")
    public String myOrders(Model model, Authentication auth) {
        model.addAttribute("orders",
                orderService.getBuyerOrders(auth.getName()));
        return "buyer/my-orders";
    }
    
}