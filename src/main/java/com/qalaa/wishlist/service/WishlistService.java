package com.qalaa.wishlist.service;

import com.qalaa.wishlist.model.Wishlist;
import com.qalaa.wishlist.wrapper.WishlistResponse;

import java.util.List;

public interface WishlistService {

    List<WishlistResponse> getWishlistByUserId(Long userId);

    WishlistResponse addToWishlist(Long userId, Long productId);

    void removeFromWishlist(Long userId,Long id);
}

