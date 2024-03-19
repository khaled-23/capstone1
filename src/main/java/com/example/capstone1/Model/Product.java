package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "id should not be empty")
    private String id;
    @NotEmpty(message = "name should not be empty")
    @Size(min=4, message = "name should be more than 3 characters")
    @Pattern(regexp = "^[a-zA-Z_ ]+$", message = "")
    private String name;
    @NotNull(message = "price should not be empty")
    @Positive(message = "price should be a positive number")
    private Double price;
    @NotEmpty(message = "category id should not be empty")
    private String categoryId;
}
