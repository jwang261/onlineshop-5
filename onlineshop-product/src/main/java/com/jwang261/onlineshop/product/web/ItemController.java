package com.jwang261.onlineshop.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jwang261
 * @date 2020/9/13 12:12 PM
 */
@Controller
public class ItemController {

    @GetMapping("/{skuId}.html")
    public String skuItem(Long skuId){

        System.out.println("=======" + skuId + "=======");

        return "item";
    }
}
