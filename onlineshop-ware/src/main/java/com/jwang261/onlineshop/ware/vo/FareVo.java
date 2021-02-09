package com.jwang261.onlineshop.ware.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jwang261
 * @date 2021/2/9 1:33 PM
 */
@Data
public class FareVo {
    private MemberAddressVo address;
    private BigDecimal fare;
}
