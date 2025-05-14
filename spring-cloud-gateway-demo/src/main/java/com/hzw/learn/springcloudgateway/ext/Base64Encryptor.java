package com.hzw.learn.springcloudgateway.ext;

import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.Base64;

/**
 * @ClassName Base64Encryptor
 * @Description TODO
 * @Author houzw
 * @Date 2024/9/18
 **/
public class Base64Encryptor implements TextEncryptor {

    @Override
    public String encrypt(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    @Override
    public String decrypt(String s) {
        byte[] decodedBytes = Base64.getDecoder().decode(s.getBytes());
        return new String(decodedBytes);
    }
}
