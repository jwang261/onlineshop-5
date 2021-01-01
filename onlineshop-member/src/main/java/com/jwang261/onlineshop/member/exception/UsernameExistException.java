package com.jwang261.onlineshop.member.exception;

/**
 * @author jwang261
 * @date 2020/12/31 8:47 PM
 */
public class UsernameExistException extends RuntimeException{
    public UsernameExistException(){
        super("用户名存在");
    }

}
