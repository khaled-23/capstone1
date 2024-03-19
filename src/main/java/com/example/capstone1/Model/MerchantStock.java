package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "merchant stock id should not be empty")
    private String id;
    @NotEmpty(message = "product id should not be empty")
    private String productId;
    @NotEmpty(message = "merchant id should not be empty")
    private String merchantId;
    @NotNull(message = "stock should not be empty")
    @Min(value = 10,message = "minimum stock at start should be 10")
    private Integer stock;
}
