package com.jwang261.onlineshop.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jwang261
 * @date 2021/1/11 9:58 AM
 */
public class Cart {
    List<CartItem> items;
    private Integer countNum; // tot num
    private Integer countType; // type num
    private BigDecimal totalAmount;

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int count = 0;
        if(items != null && items.size() > 0){
            for (CartItem item : items) {
                count += item.getCount();
            }

        }

        return count;
    }



    public Integer getCountType() {
        return items == null || items.size() == 0 ? 0 : items.size();
    }



    public BigDecimal getTotalAmount() {
        //1、计算购物项总价
        BigDecimal amount = new BigDecimal("0");
        if(items != null && items.size() > 0){
            for (CartItem item : items) {
                if(item.getCheck()){
                    amount.add(item.getTotalPrice());

                }
            }
        }

        //2、减去优惠总价
        BigDecimal subtract = amount.subtract(getReduce());
        return subtract;
    }



    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }

    private BigDecimal reduce = new BigDecimal("0.00"); // reduced price by discount
}
