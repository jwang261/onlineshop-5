package com.jwang261.onlineshop.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.jwang261.common.utils.R;
import com.jwang261.common.vo.MemberRespVo;
import com.jwang261.onlineshop.order.feign.CartFeignService;
import com.jwang261.onlineshop.order.feign.MemberFeignService;
import com.jwang261.onlineshop.order.feign.WmsFeignService;
import com.jwang261.onlineshop.order.intercepter.LoginUserInterceptor;
import com.jwang261.onlineshop.order.vo.MemberAddressVo;
import com.jwang261.onlineshop.order.vo.OrderConfirmVo;
import com.jwang261.onlineshop.order.vo.OrderItemVo;
import com.jwang261.onlineshop.order.vo.SkuStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.common.utils.Query;

import com.jwang261.onlineshop.order.dao.OrderDao;
import com.jwang261.onlineshop.order.entity.OrderEntity;
import com.jwang261.onlineshop.order.service.OrderService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    CartFeignService cartFeignService;

    @Autowired
    WmsFeignService wmsFeignService;

    @Autowired
    ThreadPoolExecutor executor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        RequestAttributes oldThreadAttribute = RequestContextHolder.getRequestAttributes();

        CompletableFuture<Void> getAddressFuture = CompletableFuture.runAsync(()->{
            RequestContextHolder.setRequestAttributes(oldThreadAttribute);
            //1.远程查询所有收货地址列表
            List<MemberAddressVo> address = memberFeignService.getAddress(memberRespVo.getId());
            confirmVo.setAddress(address);
        },executor);

        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(()->{
            RequestContextHolder.setRequestAttributes(oldThreadAttribute);
            //2.远程查询当前购物车中所选项
            List<OrderItemVo> currentUserCartItems = cartFeignService.getCurrentUserCartItems();
            System.out.println(currentUserCartItems);
            confirmVo.setItems(currentUserCartItems);

        },executor)/**.thenRunAsync(()->{
            List<OrderItemVo> items = confirmVo.getItems();
            List<Long> collect = items.stream().map(item -> item.getSkuId()).collect(Collectors.toList());
            R skusHasStock = wmsFeignService.getSkusHasStock(collect);
            List<SkuStockVo> data = skusHasStock.getData(new TypeReference<List<SkuStockVo>>() {
            });
            if(data!=null){
                Map<Long, Boolean> map = data.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                confirmVo.setStocks(map);

            }
        },executor)**/;
        //feign在远程调用之前要构造请求，创建了一个新的请求模板，用的模板默认没有任何额外信息,见MyFeignConfig
        //
        //3.积分
        Integer integration = memberRespVo.getIntegration();
        confirmVo.setIntegration(integration);
        //4.其他数据自动计算

        //5.防重令牌

        CompletableFuture.allOf(getAddressFuture,cartFuture).get();
        return confirmVo;
    }

}