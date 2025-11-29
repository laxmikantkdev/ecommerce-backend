package com.laxmikant.ecommerce.service;

import com.laxmikant.ecommerce.entity.CartItem;
import com.laxmikant.ecommerce.entity.OrderHeader;
import com.laxmikant.ecommerce.entity.User;
import com.laxmikant.ecommerce.repository.CartItemRepository;
import com.laxmikant.ecommerce.repository.OrderHeaderRepository;
import com.laxmikant.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderHeaderRepository orderHeaderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public OrderService(OrderHeaderRepository orderHeaderRepository,
                        CartItemRepository cartItemRepository,
                        UserRepository userRepository) {
        this.orderHeaderRepository = orderHeaderRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    public OrderHeader placeOrder(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        List<CartItem> items = cartItemRepository.findByUser(user);
        if (items.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        BigDecimal total = items.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderHeader order = new OrderHeader(user, total, OrderHeader.Status.CREATED);
        orderHeaderRepository.save(order);

        cartItemRepository.deleteByUser(user);

        return order;
    }

    public List<OrderHeader> getOrders(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return orderHeaderRepository.findByUser(user);
    }
}
