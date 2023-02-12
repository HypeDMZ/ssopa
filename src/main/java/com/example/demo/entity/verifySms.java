package com.example.demo.entity;

import lombok.Getter;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.springframework.data.annotation.Id;
@Getter
@RedisHash(value = "sms" , timeToLive = 180)
public class verifySms {
    @Id
    @AccessType(AccessType.Type.PROPERTY)
    private String phoneNumber;
    @Column(nullable = false)
    private String verifyCode;


    public verifySms(String phoneNumber, String verifyCode) {
        this.phoneNumber = phoneNumber;
        this.verifyCode = verifyCode;
    }
}