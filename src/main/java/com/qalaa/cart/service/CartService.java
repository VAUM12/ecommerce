package com.qalaa.cart.service;

import com.qalaa.cart.model.Cart;
import com.qalaa.cart.wrapper.CartResponse;

import java.util.List;

public interface CartService {

    public List<CartResponse> getCartByUserId(Long userId);

    public CartResponse addToCart(Long userId, Long productId, Integer quantity);

    public void removeFromCart(Long userId,Long id);

    public CartResponse updateToCart(Long userId, Long productId, Integer quantity);
}
