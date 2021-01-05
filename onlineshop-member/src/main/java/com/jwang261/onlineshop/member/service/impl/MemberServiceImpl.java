package com.jwang261.onlineshop.member.service.impl;

import com.jwang261.onlineshop.member.dao.MemberLevelDao;
import com.jwang261.onlineshop.member.entity.MemberLevelEntity;
import com.jwang261.onlineshop.member.exception.PhoneExistException;
import com.jwang261.onlineshop.member.exception.UsernameExistException;
import com.jwang261.onlineshop.member.vo.MemberLoginVo;
import com.jwang261.onlineshop.member.vo.MemberRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.common.utils.Query;

import com.jwang261.onlineshop.member.dao.MemberDao;
import com.jwang261.onlineshop.member.entity.MemberEntity;
import com.jwang261.onlineshop.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberRegisterVo vo) {
        MemberDao dao = this.baseMapper;
        MemberEntity entity = new MemberEntity();
        //设置默认等级
        MemberLevelEntity levelEntity = memberLevelDao.getDefaultLevel();
        entity.setLevelId(levelEntity.getId());

        //检查唯一性，为了让controller感知异常，使用异常机制
        checkUsernameUnique(vo.getUsername());
        checkPhoneUnique(vo.getPhone());

        entity.setMobile(vo.getPhone());
        entity.setUsername(vo.getUsername());
        entity.setNickname(vo.getUsername());

        //加密 - md5盐值加密
//        Md5Crypt.md5Crypt("123456".getBytes());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(vo.getPassword());
        entity.setPassword(encode);

        dao.insert(entity);

    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneExistException{
        MemberDao dao = this.baseMapper;
        Integer count = dao.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if(count > 0){
            throw new PhoneExistException();

        }

    }

    @Override
    public void checkUsernameUnique(String username) throws UsernameExistException{
        MemberDao dao = this.baseMapper;
        Integer count = dao.selectCount(new QueryWrapper<MemberEntity>().eq("username", username));
        if(count > 0){
            throw new UsernameExistException();

        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String account = vo.getAccount();
        String password = vo.getPassword();
        //去数据库查询
        MemberDao dao = this.baseMapper;
        MemberEntity entity = dao.selectOne(new QueryWrapper<MemberEntity>()
                .eq("username", account)
                .or().eq("mobile", account));
        if(entity == null){
            //fail
            return null;
        }else{
            String pwdDB = entity.getPassword();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean b = encoder.matches(password, pwdDB);
            if(b){
                return entity;
            }else{
                return null;
            }
        }

    }

}