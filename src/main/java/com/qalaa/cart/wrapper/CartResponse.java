package com.qalaa.cart.wrapper;

import lombok.Data;

@Data
public class CartResponse {
    private Long id;

    private Long productId;

    private String name;

    private String description;

    private String image;

    private double price;

    private Integer quantity;

    private double discount;
}
