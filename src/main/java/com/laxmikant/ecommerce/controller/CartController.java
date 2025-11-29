package com.laxmikant.ecommerce.controller;

import com.laxmikant.ecommerce.entity.CartItem;
import com.laxmikant.ecommerce.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartItem> getCart(Authentication authentication) {
        String email = authentication.getName();
        return cartService.getCart(email);
    }

    @PostMapping("/{productId}")
    public void addToCart(@PathVariable Long productId,
                          @RequestParam(defaultValue = "1") int quantity,
                          Authentication authentication) {
        String email = authentication.getName();
        cartService.addToCart(email, productId, quantity);
    }

    @DeleteMapping
    public void clearCart(Authentication authentication) {
        String email = authentication.getName();
        cartService.clearCart(email);
    }
}
