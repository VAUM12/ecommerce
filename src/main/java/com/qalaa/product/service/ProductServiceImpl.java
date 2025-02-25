package com.qalaa.product.service;

import com.qalaa.category.model.Category;
import com.qalaa.category.service.CategoryService;
import com.qalaa.exception.ResourceNotFoundException;
import com.qalaa.product.model.Product;
import com.qalaa.product.repository.ProductRepository;
import com.qalaa.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product createProduct(Product product, Long categoryId) {

        Category category= categoryService.getCategoryById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product,Long categoryId) {

        Category category= categoryService.getCategoryById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        return productRepository.findById(id).map(existProduct -> {
            existProduct.setName(product.getName());
            existProduct.setPrice(product.getPrice());
            existProduct.setCategory(category);// Associate the updated category
            existProduct.setDiscount(product.getDiscount());
            existProduct.setImage(product.getImage());
            existProduct.setDescription(product.getDescription());
            return productRepository.save(existProduct);
        }).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.delete(productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found with id: " + id)));
    }


}
