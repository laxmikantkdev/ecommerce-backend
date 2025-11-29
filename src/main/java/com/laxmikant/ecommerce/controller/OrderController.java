package com.laxmikant.ecommerce.controller;

import com.laxmikant.ecommerce.entity.OrderHeader;
import com.laxmikant.ecommerce.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public OrderHeader place(Authentication authentication) {
        String email = authentication.getName();
        return orderService.placeOrder(email);
    }

    @GetMapping
    public List<OrderHeader> getOrders(Authentication authentication) {
        String email = authentication.getName();
        return orderService.getOrders(email);
    }
}
