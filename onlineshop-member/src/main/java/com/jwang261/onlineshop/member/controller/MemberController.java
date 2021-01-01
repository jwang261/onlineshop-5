package com.jwang261.onlineshop.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.jwang261.common.exception.BizCodeEnum;
import com.jwang261.onlineshop.member.exception.PhoneExistException;
import com.jwang261.onlineshop.member.exception.UsernameExistException;
import com.jwang261.onlineshop.member.feign.CouponFeignService;
import com.jwang261.onlineshop.member.vo.MemberLoginVo;
import com.jwang261.onlineshop.member.vo.MemberRegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jwang261.onlineshop.member.entity.MemberEntity;
import com.jwang261.onlineshop.member.service.MemberService;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.common.utils.R;


/**
 * 会员
 *
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:52:51
 */
@RestController
@RequestMapping("member/member")
@Slf4j
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponFeignService couponFeignService;

    @PostMapping("login")
    public R login(@RequestBody MemberLoginVo vo) {
        MemberEntity entity = memberService.login(vo);
        if (entity != null) {
            return R.ok();
        }else{

            return R.error(BizCodeEnum.LOGIN_ACCT_PWD_INVALID_EXCEPTION.getCode(),BizCodeEnum.LOGIN_ACCT_PWD_INVALID_EXCEPTION.getMsg());
        }

    }

    @PostMapping("/register")
    public R register(@RequestBody MemberRegisterVo vo) {

        try {
            memberService.register(vo);
        } catch (PhoneExistException e) {
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        } catch (UsernameExistException e) {
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(), BizCodeEnum.USER_EXIST_EXCEPTION.getMsg());

        }
        return R.ok();
    }

    @RequestMapping("/coupons")
    public R test() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("JackeyLove");
        R memberCoupons = couponFeignService.memberCoupons();
        Object coupons = memberCoupons.get("coupons");
        return R.ok().put("member", memberEntity).put("coupons", coupons);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {

        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
