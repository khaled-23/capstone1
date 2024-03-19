package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {
    ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
    }

    public ArrayList<Product> getProducts(){
        return products;
    }

    public boolean isUpdated(String id, Product product){
        for(int i=0;i<products.size();i++){
            if(products.get(i).getId().equalsIgnoreCase(id)){
                products.set(i,product);
                return true;
            }
        }
        return false;
    }
    public boolean isDeleted(String id){
        for(int i=0; i< products.size();i++){
            if(products.get(i).getId().equalsIgnoreCase(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }
    public double getProductPrice(String productId){
        for(int i=0; i<products.size();i++){
            if(products.get(i).getId().equalsIgnoreCase(productId)){
                return products.get(i).getPrice();
            }
        }
        return 0;
    }

    public Product getProduct(String productId){
        for (Product product : products) {
            if (product.getId().equalsIgnoreCase(productId)) {
                return product;
            }
        }
        return null;
    }
    public boolean doesProductExists(String id){
        for(Product product:products){
            if(product.getId().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }

    public Product getProductCopy(String productId) {
        for (Product product : products) {
            if (product.getId().equalsIgnoreCase(productId)) {
                return new Product(product.getId(),product.getName(),product.getPrice(),product.getCategoryId());
            }
        }
        return null;
    }
}
