package com.example.demo.dto;

import com.example.demo.entity.Authority;
import com.example.demo.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    @ApiModelProperty(value="이메일", example="ssohye@icloud.com",required = true)
    private String email;
    @ApiModelProperty(value="비밀번호", example="12345678",required = true)
    private String password;
    @ApiModelProperty(value="닉네임", example="조태완바보",required = true)
    private String nickname;

    @ApiModelProperty(value="전화번호", example="01028686435",required = true)
    private String phonenumber;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .phonenumber(phonenumber)
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}