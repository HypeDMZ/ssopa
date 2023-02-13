package com.example.demo.dto;


import com.example.demo.entity.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {
    @ApiModelProperty(value="글제목", example="오늘의 공지",required = true)
    private String title;


    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .title(post.getTitle())
                .build();
    }

}