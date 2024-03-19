package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Cart;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.CartService;
import com.example.capstone1.Service.MerchantStockService;
import com.example.capstone1.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final MerchantStockService merchantStockService;
    private final ProductService productService;

    @PostMapping("/add/{userId}/{merchantId}/{productId}")
    public ResponseEntity addToCart(@PathVariable String userId, @PathVariable String merchantId,
                                    @PathVariable String productId){
        boolean doesStockExists = merchantStockService.doesMerchantAndProductExists(merchantId,productId);
        if(!doesStockExists){
            return ResponseEntity.status(400).body(new ApiResponse("stock doesn't exists"));
        }
        Product product = productService.getProduct(productId);
        cartService.addToCart(userId,merchantId,product);
        return ResponseEntity.status(200).body(new ApiResponse("item added"));
    }
    @GetMapping("/view/{userId}")
    public ResponseEntity viewCart(@PathVariable String userId){
        ArrayList<Cart> userCart = cartService.getCart(userId);
        if(userCart.isEmpty()){
            return ResponseEntity.status(400).body("cart empty");
        }else return ResponseEntity.status(200).body(userCart);
    }

    @DeleteMapping("/remove-item/{userId}/{merchantId}/{productId}")
    public ResponseEntity removeFromCart(@PathVariable String userId, @PathVariable String merchantId, @PathVariable String productId){
       boolean isRemoved = cartService.removeFromCart(userId,merchantId,productId);
       if(isRemoved){
           return ResponseEntity.status(200).body(new ApiResponse("item removed"));
       }else return ResponseEntity.status(400).body(new ApiResponse("error"));
    }
}
