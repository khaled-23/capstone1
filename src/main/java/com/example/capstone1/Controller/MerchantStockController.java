package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantService;
import com.example.capstone1.Service.MerchantStockService;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;
    private final MerchantService merchantService;
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        //checks if merchant does exist when adding merchant stock
        boolean doesMerchantExists = merchantService.doesMerchantExists(merchantStock.getMerchantId());
        if(!doesMerchantExists){
            return ResponseEntity.status(400).body(new ApiResponse("merchant does not exists"));
        }
        //checks if product does exist when adding merchant stock
        boolean doesProductExists = productService.doesProductExists(merchantStock.getProductId());
        if(!doesProductExists){
            return ResponseEntity.status(400).body(new ApiResponse("product does not exists"));
        }
        merchantStockService.addMerchantStock(merchantStock);
        return ResponseEntity.status(200).body(new ApiResponse("merchantStock added: "+merchantStock));
    }
    @GetMapping("/merchant-stocks")
    public ResponseEntity getMerchantStocks(){
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStocks();
        if(merchantStocks.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("there are no merchant Stock"));
        }else return ResponseEntity.status(200).body(merchantStocks);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        //checks if merchant does exist when updating merchant stock
        boolean doesMerchantExists = merchantService.doesMerchantExists(merchantStock.getMerchantId());
        if(!doesMerchantExists){
            return ResponseEntity.status(400).body(new ApiResponse("merchant does not exists"));
        }
        //checks if product does exist when updating merchant stock
        boolean doesProductExists = productService.doesProductExists(merchantStock.getProductId());
        if(!doesProductExists){
            return ResponseEntity.status(400).body(new ApiResponse("product does not exists"));
        }
        boolean isUpdated = merchantStockService.isUpdated(id,merchantStock);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("merchant stock updated"));
        }else return ResponseEntity.status(400).body(new ApiResponse("merchant stock not found"));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity removeMerchantStock(@PathVariable String id){
        boolean isRemoved = merchantStockService.isRemoved(id);
        if(isRemoved){
            return ResponseEntity.status(200).body("merchant stock id: "+id+" is removed");
        }else return ResponseEntity.status(400).body("merchant not found: "+ id);
    }

    @PutMapping("add-stock/{merchantId}/{productId}/{stock}")
    public ResponseEntity reStock(@PathVariable String merchantId, @PathVariable String productId, @PathVariable int stock){
        boolean doesMerchantAndProductExists = merchantStockService.doesMerchantAndProductExists(merchantId,productId);
        boolean isPositive = 0<stock;

        if(doesMerchantAndProductExists&&isPositive){
            merchantStockService.reStock(merchantId,productId,stock);
            return ResponseEntity.status(200).body("restock added");
        }else if(!doesMerchantAndProductExists){
            return ResponseEntity.status(400).body("either merchant or product does not exists or both");
        }else return ResponseEntity.status(400).body("stock should be more than 0");
    }

}
