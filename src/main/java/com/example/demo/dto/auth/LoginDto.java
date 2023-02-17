package com.example.demo.dto.auth;

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
public class LoginDto {
    @ApiModelProperty(value="이메일", example="ssohye@icloud.com",required = true)
    private String email;
    @ApiModelProperty(value="비밀번호", example="12345678",required = true)
    private String password;


    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}