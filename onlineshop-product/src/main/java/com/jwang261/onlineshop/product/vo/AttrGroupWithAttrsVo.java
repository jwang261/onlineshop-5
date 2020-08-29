package com.jwang261.onlineshop.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.jwang261.onlineshop.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author jwang261
 * @date 2020/8/28 10:04 PM
 */
@Data
public class AttrGroupWithAttrsVo {

    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    private Long attrGroupId;
    /**
     * $column.comments
     */
    private String attrGroupName;
    /**
     * $column.comments
     */
    private Integer sort;
    /**
     * $column.comments
     */
    private String descript;
    /**
     * $column.comments
     */
    private String icon;
    /**
     * $column.comments
     */
    private Long catelogId;

    private List<AttrEntity> attrs;
}
