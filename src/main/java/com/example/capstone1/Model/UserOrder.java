package com.example.capstone1.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class UserOrder {
    private String orderId;
    private String userId;
    private LocalDateTime dateTime;
    private Product products;
}
