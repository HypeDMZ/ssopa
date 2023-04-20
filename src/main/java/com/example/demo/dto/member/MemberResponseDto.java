package com.example.demo.dto.member;

import com.example.demo.entity.Authority;
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
    @ApiModelProperty(value="프로필이미지url", example="www.exmaple.com",required = true)
    private String profileImage;
    @ApiModelProperty(value="닉네임, 2~10자", example="조태완바보",required = true)
    private String nickname;
    @ApiModelProperty(value="관리자 ,유저 ", example="ROLE_ADMIN",required = true)
    private Authority authority;
    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .profileImage(member.getProfileImage())
                .nickname(member.getNickname())
                .authority(member.getAuthority())
                .build();
    }
}