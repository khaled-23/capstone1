package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import com.example.capstone1.Model.UserOrder;
import com.example.capstone1.Service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MerchantStockService merchantStockService;
    private final ProductService productService;
    private final UserOrderService userOrderService;
    private final CouponService couponService;

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("user added"));
    }

    @GetMapping("/users")
    public ResponseEntity getUsers(){
        ArrayList<User> users = userService.getUsers();
        if(users.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("there are no users"));
        }else return ResponseEntity.status(200).body(users);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        boolean isUpdated = userService.isUpdated(id,user);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("user is updated: "+id));
        }else return ResponseEntity.status(400).body(new ApiResponse("user not found"));
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity removeUser(@PathVariable String id){
        boolean isRemoved = userService.isRemoved(id);
        if(isRemoved){
            return ResponseEntity.status(200).body("user removed: "+id);
        }else return ResponseEntity.status(400).body("user not found");
    }

    @PostMapping("/buy/{userId}/{merchantId}/{productId}")
    public ResponseEntity buyProduct(@PathVariable String userId ,@PathVariable String merchantId, @PathVariable String productId){
        boolean isExists = userService.isExists(userId);
        if(!isExists){
            return ResponseEntity.status(400).body(new ApiResponse("user not found"));
        }
        boolean doesMerchantAndProductExists = merchantStockService.doesMerchantAndProductExists(merchantId, productId);
        if(!doesMerchantAndProductExists){
            return ResponseEntity.status(400).body(new ApiResponse("merchant or product does not exists"));
        }
        boolean hasStock = merchantStockService.hasStock(merchantId,productId);
        if(!hasStock){
            return ResponseEntity.status(400).body(new ApiResponse("merchant does not have stock"));
        }
        double productPrice = productService.getProductPrice(productId);
        boolean userHasEnoughBalance = productPrice<=userService.getUserBalance(userId);
        if(!userHasEnoughBalance){
            return ResponseEntity.status(400).body(new ApiResponse("user does not have enough credit"));
        }
        Product product = productService.getProductCopy(productId);//get product to added it to user order history
        userService.order(userId,productPrice);//reduce balance by product price
        userOrderService.addOrder(new UserOrder("generated!", userId,LocalDateTime.now(),product)); //order history
        merchantStockService.userOrdered(merchantId, productId);//reduce stock by 1
        return ResponseEntity.status(200).body(new ApiResponse("product ordered"));
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity getOrders(@PathVariable String userId){
        ArrayList<UserOrder> userOrders = userOrderService.getUserOrders(userId);
        return ResponseEntity.status(200).body(userOrders);
    }

    @PostMapping("/buy/{userId}/{merchantId}/{productId}/{couponKey}")
    public ResponseEntity buyWithCoupon(@PathVariable String userId, @PathVariable String merchantId,
                                             @PathVariable String productId,@PathVariable String couponKey){
        boolean isExists = userService.isExists(userId);
        if(!isExists){
            return ResponseEntity.status(400).body(new ApiResponse("user not found"));
        }
        boolean doesMerchantAndProductExists = merchantStockService.doesMerchantAndProductExists(merchantId, productId);
        if(!doesMerchantAndProductExists){
            return ResponseEntity.status(400).body(new ApiResponse("merchant or product does not exists"));
        }
        boolean hasStock = merchantStockService.hasStock(merchantId,productId);
        if(!hasStock){
            return ResponseEntity.status(400).body(new ApiResponse("merchant does not have stock"));
        }

        double productPrice = productService.getProductPrice(productId);
        double couponPercent = couponService.getCouponPercent(couponKey);
        double productPriceWithDiscount = couponService.calculatePrice(productPrice, couponPercent);
        boolean userHasEnoughBalance = productPriceWithDiscount <= userService.getUserBalance(userId);
        if(!userHasEnoughBalance){
            return ResponseEntity.status(400).body(new ApiResponse("user does not have enough credit"));
        }

        boolean isCouponValid = couponService.isCouponValid(merchantId,productId,couponKey);
        if(!isCouponValid){
            return ResponseEntity.status(400).body(new ApiResponse("coupon is invalid"));
        }
        Product product = productService.getProductCopy(productId);
        product.setPrice(productPriceWithDiscount);//to save product in order history with price after discount
        userService.order(userId,productPriceWithDiscount);
        userOrderService.addOrder(new UserOrder("generated!", userId,LocalDateTime.now(),product)); //order history
        merchantStockService.userOrdered(merchantId, productId);//reduce stock by 1
        couponService.reduceUses(couponKey);
        return ResponseEntity.status(200).body(new ApiResponse("product ordered with discount: "+couponPercent+"%, price after discount: "+productPriceWithDiscount));
    }
}
