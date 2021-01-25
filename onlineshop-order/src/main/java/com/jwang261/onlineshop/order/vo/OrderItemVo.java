package com.jwang261.onlineshop.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jwang261
 * @date 2021/1/17 1:34 PM
 */
@Data
public class OrderItemVo {
    private Long skuId;
    private String title;
    private String image;
    private List<String> skuAttr;
    private BigDecimal price;
    private Integer count;
    private BigDecimal totalPrice;
    //private boolean hasStock;
    private BigDecimal weight = new BigDecimal("0.3");

}
