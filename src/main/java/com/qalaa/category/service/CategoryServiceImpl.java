package com.qalaa.category.service;

import com.qalaa.category.model.Category;
import com.qalaa.category.repository.CategoryRepository;
import com.qalaa.exception.ResourceNotFoundException;
import com.qalaa.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(Category category) {
        if (category.getProducts() != null) {
            for (Product product : category.getProducts()) {
                product.setCategory(category); // Set the category reference for each product
            }
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        // Fetch existing category
        Category existCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        // Update basic category fields
        existCategory.setName(category.getName());
        existCategory.setDescription(category.getDescription());
        existCategory.setImage(category.getImage());

        // Map of existing products for easy lookup
        Map<Long, Product> existingProductsMap = existCategory.getProducts().stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        // List for updated products
        List<Product> updatedProducts = new ArrayList<>();

        for (Product updatedProduct : category.getProducts()) {
            if (updatedProduct.getId() != null && updatedProduct.getId() != 0) {
                // Update existing product
                Product existingProduct = existingProductsMap.get(updatedProduct.getId());
                if (existingProduct != null) {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setDescription(updatedProduct.getDescription());
                    existingProduct.setImage(updatedProduct.getImage());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setDiscount(updatedProduct.getDiscount());
                    updatedProducts.add(existingProduct);
                } else {
                    throw new ResourceNotFoundException("Product not found: " + updatedProduct.getId());
                }
            } else {
                // Add new product
                updatedProduct.setCategory(existCategory);
                updatedProducts.add(updatedProduct);
            }
        }

        // Assign updated products to the category
        existCategory.setProducts(updatedProducts);

        // Save and return updated category
        return categoryRepository.save(existCategory);
    }


    @Override
    public void deleteCategory(Long id) {
        Category existCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(existCategory);
    }
}
