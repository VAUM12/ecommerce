package com.qalaa.wishlist.controller;

import com.qalaa.util.ApiResponse;
import com.qalaa.wishlist.model.Wishlist;
import com.qalaa.wishlist.service.WishlistService;
import com.qalaa.wishlist.wrapper.WishlistResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@SecurityRequirement(name = "bearerAuth")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<WishlistResponse>>> getWishlist(@RequestAttribute("id") Long userId, HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Wishlist", wishlistService.getWishlistByUserId(userId),request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WishlistResponse>> addToWishlist(@RequestAttribute("id")Long userId, @RequestParam Long productId,HttpServletRequest request) {

        return ResponseEntity.ok(new ApiResponse<>("success", "Product added to wishlist", wishlistService.addToWishlist(userId, productId),request.getRequestURI()));}

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removeFromWishlist(@RequestAttribute("id")Long userId,@PathVariable Long id,HttpServletRequest request) {
        wishlistService.removeFromWishlist(userId,id);
        return ResponseEntity.ok(new ApiResponse<>("success", "product removed from wishlist", "Product with ID " + id + " has been successfully removed from your wishlist.",request.getRequestURI()));
    }
}
