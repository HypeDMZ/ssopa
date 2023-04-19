package com.example.demo.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class syncTokenResponseDto {
    @ApiModelProperty(value="성공여부", example="true",required = true)
    private boolean success;
}

