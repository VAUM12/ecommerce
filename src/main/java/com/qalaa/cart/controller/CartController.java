package com.qalaa.cart.controller;


import com.qalaa.cart.model.Cart;
import com.qalaa.cart.service.CartService;
import com.qalaa.cart.wrapper.CartResponse;
import com.qalaa.util.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@SecurityRequirement(name = "bearerAuth")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<CartResponse>>>getCart(@RequestAttribute("id") Long userId, HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Cart", cartService.getCartByUserId(userId),request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(@RequestAttribute("id") Long userId, @RequestParam Long productId, @RequestParam Integer quantity,HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Product added to cart", cartService.addToCart(userId, productId, quantity),request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CartResponse>> updateCart(@RequestAttribute("id") Long userId, @PathVariable Long id, @RequestParam Integer quantity,HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Product updated in cart", cartService.updateToCart(userId, id, quantity),request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removeFromCart(@RequestAttribute("id") Long userId,@PathVariable Long id,HttpServletRequest request) {
        cartService.removeFromCart(userId,id);
        return ResponseEntity.ok(new ApiResponse<>("success", "product removed from cart", "Product with ID " + id + " has been successfully removed from your cart.",request.getRequestURI()));
    }
}
