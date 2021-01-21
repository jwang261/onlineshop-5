package com.jwang261.onlineshop.order.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jwang261
 * @date 2021/1/17 1:20 PM
 */
//确认页面需要的信息

public class OrderConfirmVo {
    //收货地址列表
    List<MemberAddressVo> address;

    //所有选中的购物项
    List<OrderItemVo> items;

    //发票记录

    //Coupon信息
    Integer integration;

    @Setter @Getter    //防重
    String orderToken;

    public Integer getCount(){
        Integer i = 0;
        if(items != null){
            for (OrderItemVo item : items) {
                i += item.getCount();
            }
        }
        return i;
    }
    public BigDecimal getTotal() {
        BigDecimal bd = new BigDecimal("0");
        if(items != null){
            for (OrderItemVo item : items) {
                BigDecimal multiply = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
                bd = bd.add(multiply);
            }
        }

        return bd;
    }

    //订单总额
    //BigDecimal total;

    public BigDecimal getPayPrice() {
        return getTotal();
    }

    //应付
    BigDecimal payPrice;

    public List<MemberAddressVo> getAddress() {
        return address;
    }

    public void setAddress(List<MemberAddressVo> address) {
        this.address = address;
    }

    public List<OrderItemVo> getItems() {
        return items;
    }

    public void setItems(List<OrderItemVo> items) {
        this.items = items;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }
}
