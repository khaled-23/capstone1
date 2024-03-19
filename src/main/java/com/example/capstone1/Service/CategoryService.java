package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {
    ArrayList<Category> categories = new ArrayList<>();

    public void addCategory(Category category){
        categories.add(category);
    }
    public ArrayList<Category> getCategories(){
        return categories;
    }
    public boolean isUpdated(String id, Category category){
        for(int i=0; i<categories.size(); i++){
            if(categories.get(i).getId().equalsIgnoreCase(id)){
                categories.set(i,category);
                return true;
            }
        }
        return false;
    }
    public boolean isRemoved(String id){
        for(int i=0;i<categories.size(); i++){
            if(categories.get(i).getId().equalsIgnoreCase(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean doesCategoryExist(String categoryId) {
        for(Category category:categories){
            if(category.getId().equalsIgnoreCase(categoryId)){
                return true;
            }
        }
        return false;
    }
}
