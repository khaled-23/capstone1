package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {
    ArrayList<Merchant> merchants = new ArrayList<>();


    public void addMerchant(Merchant merchant){
        merchants.add(merchant);
    }
    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    public boolean isUpdated(String id, Merchant merchant){
        for(int i=0; i<merchants.size(); i++){
            if(merchants.get(i).getId().equalsIgnoreCase(id)){
                merchants.set(i,merchant);
                return true;
            }
        }
        return false;
    }
    public boolean isRemoved(String id){
        for(int i=0; i<merchants.size(); i++){
            if(merchants.get(i).getId().equalsIgnoreCase(id)){
                merchants.remove(i);

                return true;
            }
        }
        return false;
    }


    public boolean doesMerchantExists(String id) {
        for(Merchant merchant:merchants){
            if(merchant.getId().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }
}
