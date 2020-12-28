package com.jwang261.onlineshop.product.web;

import com.jwang261.onlineshop.product.service.SkuInfoService;
import com.jwang261.onlineshop.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ExecutionException;

/**
 * @author jwang261
 * @date 2020/9/13 12:12 PM
 */
@Controller
public class ItemController {

    @Autowired
    SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) throws ExecutionException, InterruptedException {

        System.out.println("=======" + skuId + "=======");
        SkuItemVo vo = skuInfoService.item(skuId);
        model.addAttribute("item", vo);
        //System.out.println(vo);
        return "item";
    }
}
