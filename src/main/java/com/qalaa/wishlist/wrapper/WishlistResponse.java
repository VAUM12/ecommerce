package com.qalaa.wishlist.wrapper;

import lombok.Data;

@Data
public class WishlistResponse {

    private Long id;
    private Long productId;

    private String name;

    private String description;

    private String image;

    private double price;

    private double discount;
}
