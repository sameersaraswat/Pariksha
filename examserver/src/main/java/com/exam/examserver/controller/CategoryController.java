package com.exam.examserver.controller;

import com.exam.examserver.model.exam.Category;
import com.exam.examserver.service.CategoryService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // add category

    @PostMapping("/")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category category1 = this.categoryService.addCategory(category);
        return ResponseEntity.ok(category1);
    }


    // get category by id
    @GetMapping("/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId) {

        return this.categoryService.getCategory(categoryId);

    }

    // get all categories

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {
        Set<Category> categories = this.categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    // update Category

    @PutMapping("/")
    public Category updateCategory(@RequestBody Category category) {

        return this.categoryService.updateCategory(category);
    }

    // delete Category

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        this.categoryService.deleteCategory(categoryId);
    }

}
