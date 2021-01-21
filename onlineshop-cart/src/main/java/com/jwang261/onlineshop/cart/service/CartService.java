package com.jwang261.onlineshop.cart.service;

import com.jwang261.onlineshop.cart.vo.Cart;
import com.jwang261.onlineshop.cart.vo.CartItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author jwang261
 * @date 2021/1/11 5:30 PM
 */
public interface CartService {
    //获取购物车某个购物享
    CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    //将商品添加到购物车
    CartItem getCartItem(Long skuId);

    //获取整个购物车
    Cart getCart() throws ExecutionException, InterruptedException;

    //清空购物车数据
    void clearCart(String cartKey);

    //勾选购物项
    void checkItem(Long skuId, Integer check);

    //修改购物项数量
    void changeItemCount(Long skuId, Integer num);


    //删除购物项
    void deleteItem(Long skuId);

    List<CartItem> getUserCartItems();
}
