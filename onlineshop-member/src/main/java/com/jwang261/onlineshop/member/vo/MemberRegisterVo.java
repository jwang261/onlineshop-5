package com.jwang261.onlineshop.member.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author jwang261
 * @date 2020/12/31 8:28 PM
 */
@Data
public class MemberRegisterVo {
    private String username;
    private String password;
    private String phone;
}
