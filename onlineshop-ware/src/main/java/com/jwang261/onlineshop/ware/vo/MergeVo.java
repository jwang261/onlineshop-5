package com.jwang261.onlineshop.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author jwang261
 * @date 2020/8/29 10:27 PM
 */
@Data
public class MergeVo {
    private Long purchaseId;
    private List<Long> items;
}
