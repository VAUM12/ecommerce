package com.qalaa.product.service;

import com.qalaa.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public List<Product> getAllProducts();

    public Product getProductById(Long id);

    public Product createProduct(Product product, Long categoryId);

    public Product updateProduct(Long id, Product product, Long categoryId);

    public void deleteProduct(Long id);

}
