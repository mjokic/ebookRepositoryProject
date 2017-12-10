package com.example.ebookrepository.service;

import com.example.ebookrepository.model.Category;
import com.example.ebookrepository.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Category getCategoryById(int categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    public void addEditCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(int categoryId) {
        categoryRepository.delete(categoryId);
    }
}
