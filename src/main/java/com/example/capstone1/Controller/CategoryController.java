package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("category added"));
    }

    @GetMapping("/categories")
    public ResponseEntity getCategories(){
        ArrayList<Category> categories = categoryService.getCategories();
        if(categories.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("category is empty"));
        }else return ResponseEntity.status(200).body(categories);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = categoryService.isUpdated(id, category);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("category updated: "+id));
        }else return ResponseEntity.status(400).body(new ApiResponse("category not found: "+id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id){
        boolean isRemoved = categoryService.isRemoved(id);

        if(isRemoved){
            return ResponseEntity.status(200).body(new ApiResponse("category removed: "+id));
        }else return ResponseEntity.status(400).body(new ApiResponse("category not found: "+ id));
    }
}
