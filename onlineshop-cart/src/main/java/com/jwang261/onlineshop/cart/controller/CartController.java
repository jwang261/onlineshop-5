package com.jwang261.onlineshop.cart.controller;

import com.jwang261.common.constant.AuthServerConstant;
import com.jwang261.onlineshop.cart.feign.ProductFeignService;
import com.jwang261.onlineshop.cart.interceptor.CartInterceptor;
import com.jwang261.onlineshop.cart.service.CartService;
import com.jwang261.onlineshop.cart.vo.CartItem;
import com.jwang261.onlineshop.cart.vo.UserInfoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;

/**
 * @author jwang261
 * @date 2021/1/11 5:34 PM
 */
@Controller
public class CartController {
    @Autowired
    CartService cartService;



    @GetMapping("/cart.html")
    public String cartListPage(){

        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        System.out.println(userInfoTo);
        return "cartList";
    }

    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                            Model model) throws ExecutionException, InterruptedException {
        CartItem cartItem = cartService.addToCart(skuId,num);
        model.addAttribute("item",cartItem);
        return "success";
    }
}
