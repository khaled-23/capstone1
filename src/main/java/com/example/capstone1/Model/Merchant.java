package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty(message = "id should not empty")
    private String id;
    @NotEmpty(message = "merchant name should not be empty")
    @Size(min = 4, message = "merchant name should be at least 4 characters")
    @Pattern(regexp = "^[a-zA-Z]+", message = "merchant name should only contain letters")
    private String name;
}
