/**
  * Copyright 2020 bejson.com 
  */
package com.jwang261.onlineshop.product.vo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

/**
 * Auto-generated: 2020-08-28 23:47:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Skus {

    private List<Attr> attr;
    private String skuName;
    private BigDecimal price;
    private String skuTitle;
    private Date skuSubtitle;
    private List<Images> images;
    private List<String> descar;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;

}