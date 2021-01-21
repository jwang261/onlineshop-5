package com.jwang261.onlineshop.cart.controller;

import com.jwang261.common.constant.AuthServerConstant;
import com.jwang261.onlineshop.cart.feign.ProductFeignService;
import com.jwang261.onlineshop.cart.interceptor.CartInterceptor;
import com.jwang261.onlineshop.cart.service.CartService;
import com.jwang261.onlineshop.cart.vo.Cart;
import com.jwang261.onlineshop.cart.vo.CartItem;
import com.jwang261.onlineshop.cart.vo.UserInfoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author jwang261
 * @date 2021/1/11 5:34 PM
 */
@Controller
public class CartController {
    @Autowired
    CartService cartService;


    @GetMapping("/currentUserCartItems")
    @ResponseBody
    public List<CartItem> getCurrentUserCartItems(){
        return cartService.getUserCartItems();

    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId){

        cartService.deleteItem(skuId);
        return "redirect:http://cart.jwang261-shop.com/cart.html";

    }

    @GetMapping("/changeItemCount")
    public String countItem(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num){
        cartService.changeItemCount(skuId, num);
        return "redirect:http://cart.jwang261-shop.com/cart.html";
    }

    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId, @RequestParam("check") Integer check){
        cartService.checkItem(skuId,check);
        return "redirect:http://cart.jwang261-shop.com/cart.html";

    }


    @GetMapping("/cart.html")
    public String cartListPage(Model model) throws ExecutionException, InterruptedException {

//        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
//        System.out.println(userInfoTo);
        Cart cart = cartService.getCart();
        model.addAttribute("cart",cart);
        return "cartList";
    }

    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                            RedirectAttributes ra) throws ExecutionException, InterruptedException {
        cartService.addToCart(skuId,num);
        //model.addAttribute("item",cartItem);
        ra.addAttribute("skuId",skuId);
        return "redirect:http://cart.jwang261-shop.com/addToCartSuccess.html";
    }

    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId,
                                       Model model){
        //重定向到成功页面
        CartItem item = cartService.getCartItem(skuId);
        model.addAttribute("item", item);
        return "success";
    }
}
