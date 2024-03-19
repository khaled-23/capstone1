package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.CategoryService;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        //check if category does exist when adding product
        boolean doesCategoryExist = categoryService.doesCategoryExist(product.getCategoryId());
        if(!doesCategoryExist){
            return ResponseEntity.status(400).body(new ApiResponse("category does not exists," +
                    " here a list of available categories"+categoryService.getCategories()));
        }
        productService.addProduct(product);
        return ResponseEntity.status(200).body(new ApiResponse("product added: "+product));
    }

    @GetMapping("/products")
    public ResponseEntity getProducts(){
        ArrayList<Product> products = productService.getProducts();
        if(products.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("there are no products"));
        }else return ResponseEntity.status(200).body(products);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody @Valid Product product,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        //check if category does exist when updating product
        boolean doesCategoryExist = categoryService.doesCategoryExist(product.getCategoryId());
        if(!doesCategoryExist){
            return ResponseEntity.status(400).body(new ApiResponse("category does not exists," +
                    " here a list of available categories"+categoryService.getCategories()));
        }
        boolean isUpdated = productService.isUpdated(id, product);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("product: "+id+" updated"));
        }else return ResponseEntity.status(400).body(new ApiResponse("product with id:"+id+" not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity removeProduct(@PathVariable String id){
        boolean isDeleted = productService.isDeleted(id);
        if(isDeleted){
            return ResponseEntity.status(200).body("product: "+id+" deleted");
        }else return ResponseEntity.status(400).body("product: "+id+" not found");
    }


}
