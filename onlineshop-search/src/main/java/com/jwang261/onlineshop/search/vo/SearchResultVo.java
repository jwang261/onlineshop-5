package com.jwang261.onlineshop.search.vo;

import com.jwang261.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.List;

/**
 * @author jwang261
 * @date 2020/9/6 3:43 PM
 */
@Data
public class SearchResultVo {
    //查询到的商品信息
    private List<SkuEsModel> products;

    /**
     * pageSplit info
     */
    private Integer pageNum;//cur page
    private Long total;//total records
    private Integer totalPages;//total pageNum

    private List<BrandVo> brands;//当前查询结果所有涉及到的品牌
    private List<CatalogVo> catalogs;
    private List<AttrVo> attrs;
    private List<Integer> pageNavs;

    //以上是返回给页面的所有信息

    @Data
    public static class BrandVo{
        private Long brandId;
        private String brandName;
        private String brandImg;
    }

    @Data
    public static class CatalogVo{
        private Long catalogId;
        private String catalogName;
    }
    @Data
    public static class AttrVo{
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }


}
