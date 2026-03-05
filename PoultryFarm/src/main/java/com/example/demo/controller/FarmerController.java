package com.example.demo.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Product;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;



@Controller
@RequestMapping("/farmer")
public class FarmerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        model.addAttribute("username", principal.getName());
        model.addAttribute("totalProducts", 0);
        model.addAttribute("pendingOrders", 0);
        model.addAttribute("acceptedOrders", 0);

        return "farmer/dashboard";
    }

    @GetMapping("/add-product")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute Product product,
                              Authentication auth) {

        // Fetch existing logged-in user from DB
        Optional<AppUser> farmer = userRepository.findByUsername(auth.getName());

        product.setFarmer(farmer.get());

        productService.save(product);

        return "redirect:/farmer/my-products";
    }


    @GetMapping("/my-products")
    public String myProducts(Model model, Authentication auth) {
        model.addAttribute("products",
                productService.getFarmerProducts(auth.getName()));
        return "my-products";
    }

    @GetMapping("/orders")
    public String orders(Model model, Authentication auth) {
        model.addAttribute("orders",
                orderService.getFarmerOrders(auth.getName()));
        return "order-requests";
    }

    @PostMapping("/accept-order")
    public String accept(Long orderId) {
        orderService.acceptOrder(orderId);
        return "redirect:/farmer/orders";
    }
    
    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/farmer/my-products";
    }
    
    @PostMapping("/reject-order")
    public String reject(Long orderId) {
        orderService.rejectOrder(orderId);
        return "redirect:/farmer/orders";
    }
}