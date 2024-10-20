package com.app.controller;

import com.app.entity.Category;
import com.app.other.NotFoundException;
import com.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Get all category with pagination
    @GetMapping
    public Page<Category> getAllCategories(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return categoryService.getAllCategories(page-1, size);
    }

    // Create a new category
    @PostMapping
    public ResponseEntity<Map<String, Object>> createCategory(@RequestBody Category category) {
        Long id = categoryService.saveCategory(category).getId();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Saved Successfully");
        response.put("id", id);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Category category = categoryService.getCategoryById(id);
            response.put("category", category);

            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            response.put("message", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Update category by ID
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Map<String, Object> response = new HashMap<>();
        try {
            Category cat = categoryService.updateCategory(id, category);
//            response.put("category", cat);
            response.put("message", "Updated Successfully");

            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            response.put("message", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Delete category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            categoryService.deleteCategory(id);
            response.put("message", "Category deleted successfully");
            response.put("status", HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            response.put("message", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}