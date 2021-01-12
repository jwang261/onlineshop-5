package com.jwang261.onlineshop.cart.service;

import com.jwang261.onlineshop.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

/**
 * @author jwang261
 * @date 2021/1/11 5:30 PM
 */
public interface CartService {
    CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;
}
