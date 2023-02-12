package com.example.demo.entity;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@RedisHash(value = "sms" , timeToLive = 180)
public class verifySms {
    @Id
    private String phoneNumber;
    private String verifyCode;
    private LocalDateTime createdAt;

    public verifySms(String phoneNumber, String verifyCode) {
        this.phoneNumber = phoneNumber;
        this.verifyCode = verifyCode;
        this.createdAt = LocalDateTime.now();
    }
}