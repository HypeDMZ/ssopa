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
public class PostUpdateDto {

    @ApiModelProperty(value="글제목", example="오늘의 공지",required = true)
    private String title;

    @ApiModelProperty(value="글내용", example = "수정된 내용", required=true)
    private String content;


    public static PostUpdateDto of(Post post) {
        return PostUpdateDto.builder()
                .content(post.getContent())
                .title(post.getTitle())
                .build();
    }
}
