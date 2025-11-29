package com.laxmikant.ecommerce.service;

import com.laxmikant.ecommerce.entity.CartItem;
import com.laxmikant.ecommerce.entity.Product;
import com.laxmikant.ecommerce.entity.User;
import com.laxmikant.ecommerce.repository.CartItemRepository;
import com.laxmikant.ecommerce.repository.ProductRepository;
import com.laxmikant.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository,
                       UserRepository userRepository,
                       ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<CartItem> getCart(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return cartItemRepository.findByUser(user);
    }

    public void addToCart(String email, Long productId, int quantity) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        CartItem item = new CartItem(user, product, quantity);
        cartItemRepository.save(item);
    }

    public void clearCart(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        cartItemRepository.deleteByUser(user);
    }
}
