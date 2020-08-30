package com.jwang261.onlineshop.ware.vo;

import lombok.Data;

/**
 * @author jwang261
 * @date 2020/8/30 12:20 AM
 */
@Data
public class PurchaseItemDoneVo {

    private Long itemId;
    private Integer status;
    private String reason;
}
