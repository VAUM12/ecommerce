package com.qalaa.cart.service;

import com.qalaa.cart.model.Cart;
import com.qalaa.cart.repository.CartRepository;
import com.qalaa.cart.wrapper.CartResponse;
import com.qalaa.exception.CustomException;
import com.qalaa.exception.ResourceNotFoundException;
import com.qalaa.product.model.Product;
import com.qalaa.product.service.ProductService;
import com.qalaa.user.service.UserService;
import com.qalaa.wishlist.model.Wishlist;
import com.qalaa.wishlist.wrapper.WishlistResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public List<CartResponse> getCartByUserId(Long userId) {
        List<Cart> Carts = cartRepository.findByUserId(userId);
        List<CartResponse> response = new ArrayList<>();
        for (Cart cart : Carts) {
            Product product = productService.getProductById(cart.getProductId());

            CartResponse cartResponse = new CartResponse();
            BeanUtils.copyProperties(product, cartResponse);
            cartResponse.setProductId(product.getId());
            cartResponse.setId(cart.getId());
            cartResponse.setQuantity(cart.getQuantity());

            response.add(cartResponse);
        }
        return response;
    }
    public CartResponse addToCart(Long userId, Long productId, Integer quantity) {

        userService.getUser(userId);
        productService.getProductById(productId);
        if(quantity <= 0) {
            throw new CustomException("Quantity must be greater than 0");
        }
        if(cartRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new CustomException("Product already in cart");
        }
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setQuantity(quantity);
        cartRepository.save(cart);

        CartResponse cartResponse = new CartResponse();
        BeanUtils.copyProperties(productService.getProductById(productId), cartResponse);
        cartResponse.setProductId(productId);
        cartResponse.setId(cart.getId());
        cartResponse.setQuantity(cart.getQuantity());

        return cartResponse;
    }

    @Override
    public CartResponse updateToCart(Long userId, Long id, Integer quantity) {

        if(quantity <= 0) {
            throw new CustomException("Quantity must be greater than 0");
        }

        Cart cart = cartRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cart not found with id: " + id));
        if(!cart.getUserId().equals(userId)) {
            throw new CustomException("You are not authorized to update this cart");
        }
        cart.setQuantity(quantity);
        cartRepository.save(cart);

        CartResponse cartResponse = new CartResponse();
        BeanUtils.copyProperties(productService.getProductById(cart.getProductId()), cartResponse);
        cartResponse.setProductId(cart.getProductId());
        cartResponse.setId(cart.getId());
        cartResponse.setQuantity(cart.getQuantity());
        return cartResponse;
    }

    public void removeFromCart(Long userId,Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cart not found with id: " + id));
        if (cart.getUserId().equals(userId)){
            throw new CustomException("You are not authorized to remove this cart");
        }
        cartRepository.deleteById(id);
    }
}
