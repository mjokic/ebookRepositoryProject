package com.example.ebookrepository.controller;

import com.example.ebookrepository.model.Category;
import com.example.ebookrepository.model.Status;
import com.example.ebookrepository.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/category")
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(CategoryService service) {
        this.categoryService = service;
    }


    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories == null || categories.size() == 0){
            Status status = new Status(false, "There aren't any categories!");
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);

        if (category == null) {
            Status status = new Status(false, "Category doesn't exist!");
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        categoryService.addEditCategory(category);
        return new ResponseEntity<>(
                new Status(true, "Category successfully created!"),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> editCategory(@RequestBody Category category){
        Category cat = categoryService.getCategoryById(category.getId());
        if (cat == null){
            return new ResponseEntity<>(
                    new Status(false, "Category doesn't exist!"),
                    HttpStatus.NOT_FOUND);
        }
        categoryService.addEditCategory(category);
        return new ResponseEntity<>(
                new Status(true, "Category successfully edited!"),
                HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(
                new Status(true, "Category successfully deleted!"),
                HttpStatus.OK);
    }

}
