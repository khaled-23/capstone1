package com.example.capstone1.Service;

import com.example.capstone1.Model.Cart;
import com.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {
    ArrayList<Cart> carts = new ArrayList<>();


    public void addToCart(String userId, String merchantId, Product product) {
        carts.add(new Cart(userId,merchantId,product));
    }

    public ArrayList<Cart> getCart(String userId) {
        ArrayList<Cart> userCart = new ArrayList<>();
        for(Cart cart:carts){
            if(cart.getUserId().equalsIgnoreCase(userId)){
                userCart.add(cart);
            }
        }
        return userCart;
    }

    public boolean removeFromCart(String userId, String merchantId, String productId) {
        for(int i=0; i<carts.size();i++){
            if(carts.get(i).getUserId().equalsIgnoreCase(userId)
            && carts.get(i).getMerchantId().equalsIgnoreCase(merchantId)
            && carts.get(i).getProduct().getId().equalsIgnoreCase(productId)){
                carts.remove(i);
                return true;
            }
        }
        return false;
    }
}
