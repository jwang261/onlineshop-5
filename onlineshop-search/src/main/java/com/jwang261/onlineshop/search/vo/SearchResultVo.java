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
    private List<SkuEsModel> products;

    /**
     * pageSplit
     */
    private Integer pageNum;//cur page
    private Long total;//total records
    private Integer totalPages;//total pageNum

    private List<BrandVo> brands;
    private List<CatalogVo> catalogs;
    private List<AttrVo> attrs;

    @Data
    public static class BrandVo{
        private Long brandId;
        private String brandName;
        private String brandImg;
    }
    //TODO sj
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
