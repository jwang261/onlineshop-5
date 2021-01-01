package com.jwang261.onlineshop.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.onlineshop.member.entity.MemberEntity;
import com.jwang261.onlineshop.member.exception.PhoneExistException;
import com.jwang261.onlineshop.member.exception.UsernameExistException;
import com.jwang261.onlineshop.member.vo.MemberLoginVo;
import com.jwang261.onlineshop.member.vo.MemberRegisterVo;

import java.util.Map;

/**
 * 会员
 *
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:52:51
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberRegisterVo vo);

    void checkPhoneUnique(String email) throws PhoneExistException;

    void checkUsernameUnique(String username) throws UsernameExistException;

    MemberEntity login(MemberLoginVo vo);
}

