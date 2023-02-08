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
public class TokenDto {
    @ApiModelProperty(value="승인타입", example="Bearer",required = true)
    private String grantType;
    @ApiModelProperty(value = "jwt토큰", example = "qrsfjr02rncnc03tx 처럼 긴 알수없는 문자", required = true)
    private String accessToken;
    @ApiModelProperty(value="토큰 만료 시간", example="16453543555",required = true)
    private Long tokenExpiresIn;
    @ApiModelProperty(value = "refresh토큰", example = "qwefqwfeqwef3tx 처럼 긴 알수없는 문자", required = true)
    private String refreshToken;
    @ApiModelProperty(value="토큰 만료 시간", example="16453543555",required = true)
    private Long refreshTokenExpiresIn;
}