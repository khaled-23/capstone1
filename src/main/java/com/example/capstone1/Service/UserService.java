package com.example.capstone1.Service;


import com.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service

public class UserService {
    ArrayList<User> users = new ArrayList<>();


    public void addUser(User user){
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean isUpdated(String id, User user){
        for(int i=0; i<users.size(); i++){
            if(users.get(i).getId().equalsIgnoreCase(id)){
                users.set(i,user);
                return true;
            }
        }
        return false;
    }
    public boolean isRemoved(String id){
        for(int i=0; i<users.size(); i++){
            if(users.get(i).getId().equalsIgnoreCase(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean isExists(String userId){
        for(User user : users){
            if(user.getId().equalsIgnoreCase(userId)){
                return true;
            }
        }
        return false;
    }
    public double getUserBalance(String userId){
        for(User user:users){
            if(user.getId().equalsIgnoreCase(userId)){
                return user.getBalance();
            }
        }
        return 0;
    }

    public void order(String userId,double price){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getId().equalsIgnoreCase(userId)){
                users.get(i).setBalance(users.get(i).getBalance()-price);
            }
        }
    }
}
