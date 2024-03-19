package com.example.capstone1.Service;

import com.example.capstone1.Model.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    public void addMerchantStock(MerchantStock merchantStock){
        merchantStocks.add(merchantStock);
    }

    public ArrayList<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }

    public boolean isUpdated(String id, MerchantStock merchantStock){
        for(int i=0; i<merchantStocks.size(); i++){
            if(merchantStocks.get(i).getId().equalsIgnoreCase(id)){
                merchantStocks.set(i,merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean isRemoved(String id){
        for(int i=0; i<merchantStocks.size(); i++){
            if(merchantStocks.get(i).getId().equalsIgnoreCase(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean doesMerchantAndProductExists(String merchantId, String productId){
        for(int i=0; i<merchantStocks.size(); i++){
            if(merchantStocks.get(i).getMerchantId().equalsIgnoreCase(merchantId)
                    &&merchantStocks.get(i).getProductId().equalsIgnoreCase(productId)){
                return true;
            }
        }
        return false;
    }

    public void reStock(String merchantId, String productId, int stock){
        for(int i=0; i<merchantStocks.size(); i++){
            if(merchantStocks.get(i).getMerchantId().equalsIgnoreCase(merchantId)
                    &&merchantStocks.get(i).getProductId().equalsIgnoreCase(productId)){
                merchantStocks.get(i).setStock(merchantStocks.get(i).getStock()+stock);
            }
        }
    }
    public boolean hasStock(String merchantId, String productId){
        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getMerchantId().equalsIgnoreCase(merchantId)
                    && merchantStock.getProductId().equalsIgnoreCase(productId)
                    && merchantStock.getStock() > 0) {
                return true;
            }
        }
        return false;
    }

    public void userOrdered(String merchantId, String productId) {
        for(int i=0; i<merchantStocks.size(); i++){
            if(merchantStocks.get(i).getMerchantId().equalsIgnoreCase(merchantId)
                    &&merchantStocks.get(i).getProductId().equalsIgnoreCase(productId)){
                merchantStocks.get(i).setStock(merchantStocks.get(i).getStock()-1);
            }
        }
    }

    public void remove(String merchantId) {
        for(int i=0; i<merchantStocks.size();i++){
            if(merchantStocks.get(i).getMerchantId().equalsIgnoreCase(merchantId)){
                merchantStocks.remove(i);
                break;
            }
        }
    }
}