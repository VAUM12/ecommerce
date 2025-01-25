package com.qalaa.wishlist.service;

import com.qalaa.exception.CustomException;
import com.qalaa.exception.ResourceNotFoundException;
import com.qalaa.product.model.Product;
import com.qalaa.product.service.ProductService;
import com.qalaa.user.service.UserService;
import com.qalaa.wishlist.model.Wishlist;
import com.qalaa.wishlist.repository.WishlistRepository;
import com.qalaa.wishlist.wrapper.WishlistResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    @Override
    public List<WishlistResponse> getWishlistByUserId(Long userId) {
        userService.getUser(userId);
        List<Wishlist> wishlist=wishlistRepository.findByUserId(userId);
        List<WishlistResponse>response=new ArrayList<>();
        for (Wishlist wishList : wishlist) {
            Product product=productService.getProductById(wishList.getProductId());

            WishlistResponse wishlistResponse = new WishlistResponse();
            BeanUtils.copyProperties(product, wishlistResponse);
            wishlistResponse.setProductId(product.getId());
            wishlistResponse.setId(wishList.getId());
            response.add(wishlistResponse);
        }
        return response;
    }

    @Override
    public WishlistResponse addToWishlist(Long userId, Long productId) {
        userService.getUser(userId);
        Product product=productService.getProductById(productId);
        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setProductId(productId);
        wishlistRepository.save(wishlist);
        WishlistResponse wishlistResponse = new WishlistResponse();
        BeanUtils.copyProperties(product, wishlistResponse);
        wishlistResponse.setProductId(product.getId());
        wishlistResponse.setId(wishlist.getId());
        return wishlistResponse;
    }

    @Override
    public void removeFromWishlist(Long userId,Long id) {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Wishlist not found with id: " + id));
        if(!wishlist.getUserId().equals(userId)){
            throw new CustomException("You are not authorized to remove this wishlist");
        }
        wishlistRepository.deleteById(id);
    }
}
