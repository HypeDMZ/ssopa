package com.example.demo.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenReqDto {
    @ApiModelProperty(value = "jwt토큰", example = "qrsfjr02rncnc03tx 처럼 긴 알수없는 문자", required = true)
    private String accessToken;
    @ApiModelProperty(value = "refresh토큰", example = "qrsfjr02rncnc03tx 처럼 긴 알수없는 문자", required = true)
    private String refreshToken;
}