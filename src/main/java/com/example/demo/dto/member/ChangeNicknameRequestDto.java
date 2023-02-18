package com.example.demo.dto.member;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ChangeNicknameRequestDto {
    @ApiModelProperty(value="닉네임", example="조태완바보",required = true)
    private String Nickname;
    @ApiModelProperty(value="이메일", example="ssohye@icloud.com",required = true)
    private String email;
}