package com.qalaa.product.controller;

import com.qalaa.product.model.Product;
import com.qalaa.product.service.ProductService;
import com.qalaa.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts(HttpServletRequest request) {

        return ResponseEntity.ok(new ApiResponse<>("success", "All products", productService.getAllProducts(),request.getRequestURI()));}

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id,HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "product", productService.getProductById(id),request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product,@RequestParam Long categoryId,HttpServletRequest request) {

        return ResponseEntity.ok(new ApiResponse<>("success", "product created", productService.createProduct(product,categoryId),request.getRequestURI()));}

    @PutMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id,
                                                 @RequestBody Product productDetails,
                                                 @RequestParam Long categoryId,HttpServletRequest request) {
        Product updatedProduct = productService.updateProduct(id, productDetails, categoryId);
        return ResponseEntity.ok(new ApiResponse<>("success", "product updated", updatedProduct,request.getRequestURI()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long id, HttpServletRequest request) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "product deleted", "Product with ID " + id + " has been successfully deleted.",request.getRequestURI()));
    }
}

