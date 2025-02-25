package com.qalaa.product.controller;

import com.qalaa.enums.RoleEnum;
import com.qalaa.product.model.Product;
import com.qalaa.product.service.ProductService;
import com.qalaa.util.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts(HttpServletRequest request) {

        return ResponseEntity.ok(new ApiResponse<>("success", "All products", productService.getAllProducts(), request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "product", productService.getProductById(id), request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestAttribute("id") Long userId, @RequestAttribute("role") String role, @RequestBody Product product, @RequestParam Long categoryId, HttpServletRequest request) {

        if(!role.equals(RoleEnum.ADMIN.name())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>("error", "You are not authorized to update a product", null,request.getRequestURI()));
        }
        return ResponseEntity.ok(new ApiResponse<>("success", "product created", productService.createProduct(product, categoryId), request.getRequestURI()));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@RequestAttribute("id") Long userId, @RequestAttribute("role") String role, @PathVariable Long id,
                                                              @RequestBody Product productDetails,
                                                              @RequestParam Long categoryId, HttpServletRequest request) {

        if(!role.equals(RoleEnum.ADMIN.name())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>("error", "You are not authorized to update a product", null,request.getRequestURI()));
        }
        Product updatedProduct = productService.updateProduct(id, productDetails, categoryId);
        return ResponseEntity.ok(new ApiResponse<>("success", "product updated", updatedProduct, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@RequestAttribute("id") Long userId, @RequestAttribute("role") String role, @PathVariable Long id, HttpServletRequest request) {

        if(!role.equals(RoleEnum.ADMIN.name())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>("error", "You are not authorized to update a product", null,request.getRequestURI()));
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "product deleted", "Product with ID " + id + " has been successfully deleted.", request.getRequestURI()));
    }
}

