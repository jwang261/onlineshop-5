package com.jwang261.onlineshop.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jwang261
 * @date 2020/9/1 5:59 PM
 */
//2级分类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalog2Vo {
    private String catalog1Id; //1级父分类
    private List<Catalog3Vo> catalog3List;//3级自分类
    private String id;
    private String name;


    //3级分类Vo
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Catalog3Vo{
        private String catalog2Id; //父分类id
        private String id;
        private String name;
    }


}
