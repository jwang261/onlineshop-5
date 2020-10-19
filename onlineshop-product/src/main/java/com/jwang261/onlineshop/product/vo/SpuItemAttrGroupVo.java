package com.jwang261.onlineshop.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author jwang261
 * @date 2020/10/18 10:08 PM
 */
@Data
@ToString
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<Attr> attrs;
}
