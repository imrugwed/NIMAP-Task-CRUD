package com.app.service;

import com.app.entity.Category;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<Category> getAllCategories(int page, int size);

    Category saveCategory(Category category);

    Category getCategoryById(Long id);

    Category updateCategory(Long id, Category updatedCategory);

    void deleteCategory(Long id);
}
