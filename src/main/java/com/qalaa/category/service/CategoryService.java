package com.qalaa.category.service;

import com.qalaa.category.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public List<Category> getAllCategories();

    public Optional<Category> getCategoryById(Long id);

    public Category createCategory(Category category);

    public Category updateCategory(Long id,Category category);

    public void deleteCategory(Long id);
}
