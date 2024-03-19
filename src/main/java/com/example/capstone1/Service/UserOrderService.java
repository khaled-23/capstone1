package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.UserOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserOrderService {
    ArrayList<UserOrder> userOrders = new ArrayList<>();
    public void addOrder(UserOrder userOrder){
        userOrders.add(userOrder);
    }

    public ArrayList<UserOrder> getUserOrders(String userId) {
        ArrayList<UserOrder> userOrdersById = new ArrayList<>();
        for(UserOrder userOrder:userOrders){
            if(userOrder.getUserId().equalsIgnoreCase(userId)){
                userOrdersById.add(userOrder);
            }
        }
        return userOrdersById;
    }

}
