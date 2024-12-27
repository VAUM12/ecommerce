package com.qalaa.category.controller;

import com.qalaa.category.model.Category;
import com.qalaa.category.service.CategoryService;
import com.qalaa.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories(HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "All categories", categoryService.getAllCategories(), request.getRequestURI()));
//        return new ResponseEntity<>(new ApiResponse<>("success", "All categories", categoryService.getAllCategories(), "/api/categories"), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Category>>> getCategoryById(@PathVariable Long id,HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "category", categoryService.getCategoryById(id), request.getRequestURI()));

    }
    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody Category category,HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "category created", categoryService.createCategory(category), request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails,HttpServletRequest request) {
            Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
            return ResponseEntity.ok(new ApiResponse<>("success", "category updated", updatedCategory, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Long id,HttpServletRequest request) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "category deleted", "Category with ID " + id + " has been successfully deleted.", request.getRequestURI()));
    }
}
