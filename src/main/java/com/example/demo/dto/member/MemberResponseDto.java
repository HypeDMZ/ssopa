package com.example.demo.dto.member;

import com.example.demo.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    @ApiModelProperty(value="이메일", example="ksdk6145@naver.com",required = true)
    private String email;
    @ApiModelProperty(value="이름, 2~10자", example="조태완",required = true)
    private String name;
    @ApiModelProperty(value="닉네임, 2~10자", example="조태완바보",required = true)
    private String nickname;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .build();
    }
}