package com.jwang261.onlineshop.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.jwang261.common.utils.R;
import com.jwang261.onlineshop.ware.feign.MemberFeignService;
import com.jwang261.onlineshop.ware.vo.FareVo;
import com.jwang261.onlineshop.ware.vo.MemberAddressVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.common.utils.Query;

import com.jwang261.onlineshop.ware.dao.WareInfoDao;
import com.jwang261.onlineshop.ware.entity.WareInfoEntity;
import com.jwang261.onlineshop.ware.service.WareInfoService;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Autowired
    MemberFeignService memberFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                new QueryWrapper<WareInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");

        if(!StringUtils.isEmpty(key)){
            wrapper.eq("id", key).or().like("name", key).
                    or().like("address", key).
                    or().like("areacode", key);
        }

        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public FareVo getFare(Long addrId) {
        FareVo fareVo = new FareVo();
        R r = memberFeignService.addrInfo(addrId);

        MemberAddressVo data = r.getData("memberReceiveAddress",
                new TypeReference<MemberAddressVo>() {
        });
        // 结合快递物流接口
        if(data != null){
            String phone = data.getPhone();
            //伪造运费
            String str = phone.substring(phone.length() - 1, phone.length());
            BigDecimal bigDecimal = new BigDecimal(str);
            fareVo.setAddress(data);
            fareVo.setFare(bigDecimal);

            return fareVo;
        }
        return null;
    }

}