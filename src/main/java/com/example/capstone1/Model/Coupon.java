package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coupon {
    @NotEmpty(message = "coupon id should not be empty")
    private String couponId;
    @NotEmpty(message = "merchant id should not be empty")
    private String merchantId;
    @NotEmpty(message = "product id should not be empty")
    private String productId;
    @NotEmpty(message = "coupon key should not be empty")
    private String couponKey;
    @NotNull(message = "percent should not be empty")
    @Min(value = 10,message = "minimum discount percent is 10")
    @Max(value = 100,message = "maximum discount percent is 100")
    @Positive(message = "discount percent should be positive")
    private Integer percent;
    @NotNull(message = "coupon uses should not be empty")
    @Positive(message = "coupon uses should be positive")
    @Min(value = 1,message = "minimum uses should be 1")
    private Integer uses;
}
