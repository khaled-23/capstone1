package com.example.capstone1.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cart {
    private String userId;
    private String merchantId;
    private Product product;
}
