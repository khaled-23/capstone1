package com.example.capstone1.Service;

import com.example.capstone1.Model.Coupon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CouponService {
    ArrayList<Coupon> coupons = new ArrayList<>();


    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    public boolean isCouponValid(String merchantId, String productId, String couponKey) {
        for(Coupon coupon:coupons){
            if(coupon.getMerchantId().equalsIgnoreCase(merchantId)
            &&coupon.getProductId().equalsIgnoreCase(productId)
            &&coupon.getCouponKey().equalsIgnoreCase(couponKey)
            &&coupon.getUses()>0){
                return true;
            }
        }
        return false;
    }

    public int getCouponPercent(String couponKey) {
        for(Coupon coupon:coupons){
            if(coupon.getCouponKey().equalsIgnoreCase(couponKey)){
                return coupon.getPercent();
            }
        }
        return 0;
    }

    public double calculatePrice(double productPrice, double couponPercent) {
        return productPrice - productPrice * (couponPercent / 100);
    }

    public void reduceUses(String couponKey) {
        for(Coupon coupon:coupons){
            if(coupon.getCouponKey().equalsIgnoreCase(couponKey)){
                coupon.setUses(coupon.getUses()-1);
                break;
            }
        }
    }
}
