package com.jwang261.onlineshop.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jwang261.common.utils.R;
import com.jwang261.onlineshop.cart.feign.ProductFeignService;
import com.jwang261.onlineshop.cart.interceptor.CartInterceptor;
import com.jwang261.onlineshop.cart.service.CartService;
import com.jwang261.onlineshop.cart.vo.CartItem;
import com.jwang261.onlineshop.cart.vo.SkuInfoVo;
import com.jwang261.onlineshop.cart.vo.UserInfoTo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jwang261
 * @date 2021/1/11 5:30 PM
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    ThreadPoolExecutor executor;

    private final String CART_PREFIX = "jwang261-shop:cart:";

    @Override
    public CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        //cart:
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String res = (String) cartOps.get(skuId.toString());
        if(StringUtils.isEmpty(res)){
            //没商品
            //添加新商品

            CartItem cartItem = new CartItem();
            CompletableFuture<Void> getSkuInfoTask = CompletableFuture.runAsync(() -> {
                //1、远程查询当前添加商品信息
                R skuInfo = productFeignService.getSkuInfo(skuId);
                SkuInfoVo data = skuInfo.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                });
                //2、商品添加到购物车
                cartItem.setCheck(true);
                cartItem.setImage(data.getSkuDefaultImg());
                cartItem.setCount(num);
                cartItem.setPrice(data.getPrice());
                cartItem.setSkuId(skuId);
                cartItem.setTitle(data.getSkuTitle());
            },executor);


            //3、远程查询sku的组合信息
            CompletableFuture<Void> getSkuSaleAttrValues = CompletableFuture.runAsync(() -> {
                List<String> values = productFeignService.getSkuSaleAttrValues(skuId);
                cartItem.setSkuAttr(values);
            }, executor);
            CompletableFuture.allOf(getSkuInfoTask,getSkuSaleAttrValues).get();
            String s = JSON.toJSONString(cartItem);
            cartOps.put(skuId.toString(),s);
            return cartItem;
        }else{
            //修改数量
            CartItem cartItem = new CartItem();
            cartItem = JSON.parseObject(res, CartItem.class);
            cartItem.setCount(cartItem.getCount() + num);
            cartOps.put(skuId.toString(),JSON.toJSONString(cartItem));
            return cartItem;
        }
    }

    private BoundHashOperations<String, Object, Object> getCartOps() {
        String cartKey = "";
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if(userInfoTo.getUserId() != null){
            cartKey = CART_PREFIX + userInfoTo.getUserId();
        }else{
            cartKey = CART_PREFIX + userInfoTo.getUserKey();
        }


        return redisTemplate.boundHashOps(cartKey);
    }
}
