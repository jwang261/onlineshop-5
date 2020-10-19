package com.jwang261.onlineshop.product.vo;

import com.jwang261.onlineshop.product.entity.SkuImagesEntity;
import com.jwang261.onlineshop.product.entity.SkuInfoEntity;
import com.jwang261.onlineshop.product.entity.SpuInfoDescEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author jwang261
 * @date 2020/10/18 2:48 PM
 */

@Data
public class SkuItemVo {

    SkuInfoEntity info;

    boolean hasStock = true;

    //2.sku的图片信息 pms_sku_images
    List<SkuImagesEntity> images;

    //3.获取spu的销售属性组合
    List<SkuItemSaleAttrVo> saleAttr;

    //c.205遗留下的空白，貌似已经补齐基本的属性和方法

    //4.获取spu的介绍
    SpuInfoDescEntity desp;

    //5.获取spu的规格参数信息
    List<SpuItemAttrGroupVo> groupAttrs;


}
