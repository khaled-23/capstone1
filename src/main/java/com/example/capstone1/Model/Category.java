package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    @NotEmpty(message = "category id should not be empty")
    private String id;
    @NotEmpty(message = "category name should not be empty")
    @Size(min = 4,message = "category name should be at least 4 characters")
    private String name;
}
