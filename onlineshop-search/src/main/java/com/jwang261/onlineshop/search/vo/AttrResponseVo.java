package com.jwang261.onlineshop.search.vo;

import lombok.Data;

/**
 * @author jwang261
 * @date 2020/12/25 12:52 AM
 */
@Data
public class AttrResponseVo {
    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    private Long attrId;
    /**
     * $column.comments
     */
    private String attrName;
    /**
     * $column.comments
     */
    private Integer searchType;
    /**
     * $column.comments
     */
    private String icon;
    /**
     * $column.comments
     */
    private String valueSelect;
    /**
     * $column.comments
     */
    private Integer attrType;
    /**
     * $column.comments
     */
    private Long enable;
    /**
     * $column.comments
     */
    private Long catelogId;
    /**
     * $column.comments
     */
    private Integer showDesc;


    private Long attrGroupId;
    private String catelogName;
    private String groupName;
    private Long[] catelogPath;
}
